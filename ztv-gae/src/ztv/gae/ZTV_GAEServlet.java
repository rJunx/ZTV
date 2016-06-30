package ztv.gae;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import ztv.core.TestConfig;

@SuppressWarnings("serial")
public class ZTV_GAEServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Welcome to Zendesk Ticket Viewer");

		try {	
			GAEURLFetcher fetcher = new GAEURLFetcher();
			fetcher.start(TestConfig.url + "tickets.json", TestConfig.user, TestConfig.pwd);
			JSONObject json = new JSONObject(fetcher.getText());
			
			if (json.has("error")) {
				resp.getWriter().println(json.getString("error"));
			}
			
			if (json.has("tickets")) {
				JSONArray array = (JSONArray)json.get("tickets");
				
				String fm = "%-50s%-10s";
				resp.getWriter().println(String.format(fm, "Subject", "Created Date"));
				
				for (int i = 0; i < array.length(); i++) {
					JSONObject item = (JSONObject)array.get(i);
					resp.getWriter().println(String.format(fm, item.getString("subject"), item.getString("created_at")));
				} 
			}
		}
		catch (Exception e) {
			
		}
	}
}
