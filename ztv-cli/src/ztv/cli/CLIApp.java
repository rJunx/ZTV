package ztv.cli;

import java.util.ArrayList;

import ztv.core.IFetcher;
import ztv.core.TestConfig;
import ztv.core.Ticket;

import org.json.JSONArray;
import org.json.JSONObject;

public class CLIApp {
	static protected ArrayList<Ticket> ticketPool = new ArrayList<Ticket>();

	public void showTicket() {
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IFetcher fetcher = new HttpClientFetcher();
		
		try {
			fetcher.start(TestConfig.url, TestConfig.user, TestConfig.pwd);
			System.out.println(fetcher.getText());
			
			JSONObject json = new JSONObject(fetcher.getText());
			
			JSONArray array = (JSONArray) json.get("tickets");
			
			for (int i = 0; i < array.length(); i++) {
				JSONObject item = (JSONObject) array.get(i);
				
				Ticket t = new Ticket();
				t.setSubject((String)item.get("subject"));
				t.setDescription((String)item.get("description"));
				t.setDate((String)item.get("created_at"));
				
				ticketPool.add(t);
			}
			
			for (int i = 0; i < ticketPool.size(); i++) {
				Ticket t = ticketPool.get(i);
				System.out.println(String.format("%s%s", t.getSubject(), t.getCreated_at()));
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

}
