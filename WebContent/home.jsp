<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Home</title>
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
               min-width: 200px;
               text-align: center;
               margin-left:10%;
               margin-right:10%;
               margin-top:5%;
               margin-bottom:0%;
            }

            .img-group-sett{
               margin-top:10%
               margin-bottom:0%;
               margin-left:17%;
               margin-right:0%;
            }

            .img-sett{
               width: 200px;
               height: 200px; 
               margin-top:10%
               margin-bottom:0%;
               margin-left:10%;
               margin-right:0%;
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
	%>
    
    <div>
        <div class="container-fluid" style="background-color:#222">
            <img class="img-logo" src="Images/logoET.png" alt="LogoET">
                </div>
    </div>
    
    <nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="197">
        <div class="container-fluid">
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="home.jsp">Home</a></li>
                        <li><a href="uploadPhoto.jsp">Upload photo</a></li>
                        <li><a href="searchFollower.jsp">Search follower</a></li>
                        <li><a href="profile.jsp">Profile</a></li>
                        <li><a href="notifs.jsp">Notifications</a></li>
                        <li><a href="settings.jsp">Settings</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="logout.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                    </ul>
                </div>
            </div>
    </nav>
    
    <div class="img-group-sett container-fluid">
        <div style="float:left; width:330px; height:300px; background-color:#FFF;">
            <img class="img-sett" src="Images/join.png" class="img-rounded" alt="Join">
            <a href="join.jsp" class="btn btn-warning btn-sett-size">Join to a travel</a>
        </div>
        
        <div style="float:left; width:330px; height:300px; background-color:#FFF;">
            <img class="img-sett" src="Images/CreatePrivateTravel.png" class="img-rounded" alt="Plan">
            <a href="plan.jsp" class="btn btn-warning btn-sett-size">Plan a travel</a>
        </div>
        
        <div style="float:left; width:315px; height:300px; background-color:#FFF;">
            <img class="img-sett" src="Images/manage.png" class="img-rounded" alt="Manage">
            <a href="manage.jsp" class="btn btn-warning btn-sett-size">Manage your travels</a>
        </div>
    </div>
    </body>
</html>