<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Settings</title>
        <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
       
       <style>
           .img-logo {
               display: block;
               margin-left: 30px;
               margin-right: auto;
               height:200px;
           }
       
           .btn-sett-size{
               width: 200px;
               min-width: 150px;
               margin-left:10%;
               margin-right:0%;
               margin-top:15%;
               margin-bottom:0%;

           }

           .img-group-sett{
               margin-left:17%;
               margin-right:15%;
               margin-top:7%;
               
           }
       
           .img-sett{
               width:200px;
               height:200px;
               margin-left:10%;
               margin-top:15px;
               margin-bottom:0%;

           }
       
           .affix {
               top: 0;
               width: 100%;
               z-index: 9999 !important;
           }
       
       
           .affix + .container-fluid {
               padding-top: 70px;
           }
       
       
       
       </style>
       
       
    </head>
    <body>
    <%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // Proxies

		if(session.getAttribute("user") == null) {
			response.sendRedirect("login.jsp");
		}
		else {
	%>
    
    <div>
        <div class="container-fluid" style="background-color:#222">
            <img class="img-logo" src="Images/logoET.png">
                </div>
        
    </div>
    
    
    <nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="197">
        <div class="container-fluid">
	        <div class="collapse navbar-collapse" id="myNavbar">
	            <ul class="nav navbar-nav">
	                <li><a href="home.jsp">Home</a></li>
	                <li><a href="uploadPhoto.jsp">Upload photo</a></li>
	                <li><a href="searchFollower.jsp">Search follower</a></li>
	                <li><a href="profile.jsp">Profile</a></li>
	                <li><a href="notifs.jsp">Notifications</a></li>
	                <li class="active"><a href="settings.jsp">Settings</a></li>
	            </ul>
	            <ul class="nav navbar-nav navbar-right">
	                <li><a href="logout.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
	            </ul>
	        </div>
	    </div>
    </nav>
    
    <div class="img-group-sett container-fluid">
        
        <div style="float:left;width:300px; height:300px; background-color:#FFF;">
            <img class="img-sett" src="Images/info.png" class="img-rounded" alt="Personal Informations">
            <a href="modifyUserInfo.jsp" class="btn btn-warning btn-sett-size">Personal Informations</a>
        </div>
        
        <div style="float:left;width:300px; height:300px; background-color:#FFF;">
            <img class="img-sett" src="Images/li.jpeg" class="img-rounded" alt="My Activity">
            <a href="#" class="btn btn-warning btn-sett-size">My Activity</a>
        </div>
        
        <div style="float:left;width:300px; height:300px; background-color:#FFF;">
            <img class="img-sett" src="Images/pa.png" class="img-rounded" alt="Payment Methods">
            <a href="#" class="btn btn-warning btn-sett-size">Payment Methods</a>
        </div>

    </div>
    <%
		}
    %>
    </body>
</html>


