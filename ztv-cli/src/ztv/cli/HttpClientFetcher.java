package ztv.cli;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Realm;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

import ztv.core.IFetcher;

public class HttpClientFetcher implements IFetcher {
	private Response response;
	private AsyncHttpClient client;
	private Realm realm;

	@Override
	public void start(String url, String userName, String pwd) throws Exception {
		// TODO Auto-generated method stub		
		client = new AsyncHttpClient();
		realm = new Realm.RealmBuilder()
				.setScheme(Realm.AuthScheme.BASIC)
				.setPrincipal(userName)
				.setPassword(pwd)
				.setUsePreemptiveAuth(true)
				.build();
		
		RequestBuilder builder = new RequestBuilder("GET");
		builder.setRealm(realm);
		builder.setUrl(url);
		builder.setHeader("Content-Type", "application/json; charset=UTF-8");
		Request request = builder.build();
		ListenableFuture<Response> future;
		future = client.executeRequest(request);
		response = future.get();
	}
	
	@Override
	public int getStatusCode() {
		// TODO Auto-generated method stub
  		if (response != null) {
			return response.getStatusCode();
		} else {
			return -1;
		}
	}

	@Override
	public String getText() throws Exception {
		// TODO Auto-generated method stub
		if (response != null) {
			return response.getResponseBody();
		} else {
			return "";
		}
	}
}
