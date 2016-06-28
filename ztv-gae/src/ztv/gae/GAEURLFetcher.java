package ztv.gae;

import java.net.URL;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import ztv.core.IFetcher;

public class GAEURLFetcher implements IFetcher {
	private HTTPResponse response;
	
	@Override
	public void start(String url, String userName, String pwd) throws Exception {
		// TODO Auto-generated method stub

		URL fetchURL = new URL(url);
    	StringBuilder buf = new StringBuilder(userName);
    	buf.append(':');
    	buf.append(pwd);

    	String authorizationString = "Basic " + com.google.appengine.repackaged.com.google.api.client.util.Base64.encodeBase64URLSafeString(buf.toString().getBytes());

    	HTTPRequest request = new HTTPRequest(fetchURL);
    	request.addHeader(new HTTPHeader("Authorization", authorizationString));
		response = URLFetchServiceFactory.getURLFetchService().fetch(request);
	}

	@Override
	public int getStatusCode() {
		// TODO Auto-generated method stub
		if (response != null) {
			return response.getResponseCode();
		} else {
			return -1;
		}
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		if (response != null) {
			return new String(response.getContent());
		} else {
			return "";
		}
	}
}
