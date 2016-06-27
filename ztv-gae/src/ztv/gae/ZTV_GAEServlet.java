package ztv.gae;

import java.io.IOException;

import javax.servlet.http.*;
import java.net.URL;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

import ztv.core.TestConfig;

@SuppressWarnings("serial")
public class ZTV_GAEServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		
		URL url = new URL(TestConfig.url);
    	StringBuilder buf = new StringBuilder(TestConfig.user);
    	buf.append(':');
    	buf.append(TestConfig.pwd);

    	String authorizationString = "Basic " + com.google.appengine.repackaged.com.google.api.client.util.Base64.encodeBase64URLSafeString(buf.toString().getBytes());
    	
    	resp.getWriter().println(authorizationString);

    	HTTPRequest request = new HTTPRequest(url);
    	request.addHeader(new HTTPHeader("Authorization", authorizationString));
		HTTPResponse response = URLFetchServiceFactory.getURLFetchService().fetch(request);
		
		resp.getWriter().println(response.getResponseCode());
		resp.getWriter().println(new String(response.getContent()));
	}
}
