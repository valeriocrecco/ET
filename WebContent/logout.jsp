<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>EasyTavel - Logout</title>
    </head>
    <body>   	    	
    	<%
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response.setHeader("Expires", "0"); // Proxies
		
			session.removeAttribute("user");
			session.invalidate();
			response.sendRedirect("login.jsp");
		%>
    </body>
</html>