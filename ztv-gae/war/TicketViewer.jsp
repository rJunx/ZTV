<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ztv.gae.GAEURLFetcher" %>
<%@ page import="ztv.core.TestConfig" %>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONArray" %>
<%@ page import="com.google.appengine.labs.repackaged.org.json.JSONObject" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<p>Welcome</p>
	<%
		GAEURLFetcher fetcher = new GAEURLFetcher();
		fetcher.start(TestConfig.url, TestConfig.user, TestConfig.pwd);
		JSONObject json = new JSONObject(fetcher.getText());
		JSONArray array = (JSONArray)json.get("tickets");
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject item = (JSONObject)array.get(i);
			pageContext.setAttribute("subject", (String)item.get("subject"));
			pageContext.setAttribute("Date", (String)item.get("created_at"));
		}
	%>
	<p><b>${fn:escapeXml(subject)}</b></p>
	<blockquote>${fn:escapeXml(Date)}</blockquote>
</body>
</html>