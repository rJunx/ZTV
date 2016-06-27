package test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.ning.http.client.*;

import ztv.core.TestConfig;

public class TestConnect {
	AsyncHttpClient client = new AsyncHttpClient();
	
	Realm realm = new Realm.RealmBuilder()
			.setScheme(Realm.AuthScheme.BASIC)
			.setPrincipal(TestConfig.user)
			.setPassword(TestConfig.pwd)
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
			builder.setUrl(TestConfig.url);
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
