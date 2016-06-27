package test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.ning.http.client.*;

public class TestConnect {
	AsyncHttpClient client = new AsyncHttpClient();
	String url = "https://codechallengetest.zendesk.com/api/v2/tickets.json";
	String user = "joon_shiesh@hotmail.com";
	String pwd = "Jun19890206!";
	
	Realm realm = new Realm.RealmBuilder()
			.setScheme(Realm.AuthScheme.BASIC)
			.setPrincipal(user)
			.setPassword(pwd)
			.setUsePreemptiveAuth(true)
			.build();
	
	public void close() {
        if (!client.isClosed()) {
            client.close();
        }
	}

	@Test
	public void test() {
		try {
			RequestBuilder builder = new RequestBuilder("GET");
			builder.setRealm(realm);
			builder.setUrl(url);
			builder.setHeader("Content-Type", "application/json; charset=UTF-8");
			Request request = builder.build();
			
			ListenableFuture<?> future = client.executeRequest(request, new AsyncCompletionHandler<Response>() {
	            @Override
	            public Response onCompleted(Response response) throws Exception {
	            	assertEquals(200, response.getStatusCode());
	            	close();
	            	return response;
	            }
	        });
			
			future.get();
			
		} catch (Exception e) {
			fail(e.getMessage());
			close();
		}
	}

}
