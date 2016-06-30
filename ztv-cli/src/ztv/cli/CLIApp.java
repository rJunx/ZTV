package ztv.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ztv.core.IFetcher;
import ztv.core.PageController;
import ztv.core.TestConfig;
import ztv.core.Ticket;

import org.json.JSONArray;
import org.json.JSONObject;

public class CLIApp {
	private Scanner scanner = new Scanner(System.in);

	private int fetchInt() {
		do {
			try {
				System.out.print("Input: ");
				int value = scanner.nextInt();
				return value;
			} catch (Exception e){
				System.out.println("Invalid Input.");
			}	
		} while(true);
	}
	
	private List<Ticket> getTickets() {
		ArrayList<Ticket> tickets = null;
		
        try {
    		IFetcher fetcher = new HttpClientFetcher();
			fetcher.start(TestConfig.url + "tickets.json", TestConfig.user, TestConfig.pwd);
			JSONObject json = new JSONObject(fetcher.getText());
			
			JSONArray array = (JSONArray) json.get("tickets");
			
			tickets = new ArrayList<Ticket>();
			for (int i = 0; i < array.length(); i++) {
				Ticket t = new Ticket();
				fillTicket(t, (JSONObject) array.get(i));
				tickets.add(t);
			}
        } catch(Exception e) {
        	System.out.println(e);
        }
        
        return tickets;
	}
	
	private Ticket getTicket(int id) {
		Ticket selected = null;
        try {
    		IFetcher fetcher = new HttpClientFetcher();
    		String url = String.format(TestConfig.url + "tickets/%d.json", id);
			fetcher.start(url, TestConfig.user, TestConfig.pwd);
			JSONObject json = new JSONObject(fetcher.getText());
			
			if (json.has("error")) {
				handleError((String)json.get("error"));
			} else {
				selected = new Ticket();
				fillTicket(selected, (JSONObject)json.get("ticket"));
			}
        } catch(Exception e) {
        	handleError(e);
        }
        
    	return selected;
	}
	
	private void fillTicket(Ticket t, JSONObject item) {
		t.setSubject(item.getString("subject"));
		t.setDescription(item.getString("description"));
		t.setDate(item.getString("created_at"));
	}
	
	private void showPage(PageController<Ticket> pc) {
        String nextLine;
        List<Ticket> pageList = null;
        
    	if (pc.hasNext())
    		pageList = pc.next();
    	
    	String fm = "%-50s%-10s";
        
		do {
            nextLine = scanner.nextLine();
            if ("q".equals(nextLine)) {
                break;
            } else if ("a".equals(nextLine)) {
            	if (pc.hasPrevious()) {
            		pageList = pc.previous();
            	}
            } else if ("d".equals(nextLine)) {
            	if (pc.hasNext())
            		pageList = pc.next();
            }
            
            if (pageList != null) {
            	System.out.println(String.format(fm, "Subject", "Created Date"));
            	for (int i = 0; i < pageList.size(); i++) {
            		Ticket t = pageList.get(i);
            		System.out.println(String.format(fm, t.getSubject(), t.getCreated_at()));
            	}
                System.out.println("Page: " + pc.getCurrentPage() + "/" + pc.getTotalPage());
                System.out.println("Input q back to menu; Input a to next page; Input d to previous page;");
            }
            
		} while(true);
	}
	
	private void showAllTickets() {
        PageController<Ticket> pc = new PageController<Ticket>();
        List<Ticket> tickets = getTickets();
        if (tickets != null) {
    		pc.bind(tickets);	
    		showPage(pc);
        }
	}
	
	private void ShowATicket() {
		System.out.print("Ticket Id ");
		int id = fetchInt();
		Ticket t = getTicket(id);
		if (t == null) {
			return;
		}
		
		System.out.println("Subject : " + t.getSubject());
		System.out.println("Created Date : " + t.getCreated_at());
	}
	
	private void handleError(String msg) {
		System.out.println(msg);
	}
	
	private void handleError(Exception e) {
    	System.out.println(e.getMessage());
	}
	
	private void menu() {
		System.out.println();
		System.out.println("Weclome to Zendesk Ticket Viewer");
		System.out.println("1. Show All Tickets");
		System.out.println("2. Show a Ticket");
		System.out.println("3. Exit");
	}
	
	public void run() {
		do {
			menu();
			int option = fetchInt();

			switch(option) {
			case 1:
				showAllTickets();
				break;
			case 2:
				ShowATicket();
				break;
			case 3:
				System.out.println("Thanks for using. Bye.");
				return;
			default:
				System.out.println("Out of service.");
			}
		} while(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CLIApp app = new CLIApp();
		app.run();
	}

}
