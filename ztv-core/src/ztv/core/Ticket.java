package ztv.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
	private String subject;
	private Date created_at;
	private String description;
	
    public String getSubject() {  
        return subject;  
    }  
  
    public void setSubject(String subject) {  
        this.subject = subject;  
    }  
  
    public Date getCreated_at() { 
        return created_at;  
    }  
  
    public void setDate(String dateStr) {
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    	try {
			this.created_at = formatter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
  
    public String getDescription() {
        return description;  
    }  
  
    public void setDescription(String description) {  
        this.description = description;  
    }  
}
