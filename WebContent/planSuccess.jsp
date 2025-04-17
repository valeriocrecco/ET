<%@page import="logic.bean.UserBean"%>
<%@page import="logic.exceptions.TravRoomException"%>
<%@page import="logic.exceptions.DatesException"%>
<%@page import="logic.exceptions.NumRoomsSyntaxException"%>
<%@page import="logic.exceptions.NumTravSyntaxException"%>
<%@page import="logic.exceptions.DestinationSyntaxException"%>
<%@page import="logic.exceptions.TravelNameSyntaxException"%>
<%@page import="java.io.IOException"%>
<%@page import="logic.controllers.PlanController"%>
<%@page import="logic.bean.PublicTravelBean"%>
<%@page import="logic.bean.PrivateTravelBean"%>
<%@page import="logic.bean.HotelBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Plan Success</title>
        <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
                <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
                    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
					<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
 
       <style>
       
           .img-logo {
               display: block;
               margin-left: 30px;
               margin-right: auto;
           }
       
       
           .create-form-first{
               float:left;
               width:400px;
               margin-top:1%;
               margin-bottom:10%;
               margin-left:20%;
           }
       
       
           .btn-create{
               float:right;
               width:30px;

               margin-top:20%;
               margin-bottom:30%;
               margin-right:35%;
           }
       
       
           .text-st{
               float:right;
               text-align:right;
               margin-right:70px;
               margin-top:40px;
               color:#f0ad4e;
           }
       
           .affix {
               top: 0;
               width: 100%;
               z-index: 9999 !important;
           }

           .img-group-sett{
           	   margin-left:0%;
               margin-right:0%;
               margin-top:0%;
               
           }
           
           
           .affix + .container-fluid {
               padding-top: 70px;
           }
           
           * {
			  box-sizing: border-box;
			}
			
			/* Create two equal columns that floats next to each other */
			.column {
			  float: left;
			  width: 50%;
			  padding: 50px;
			  height: 150px; /* Should be removed. Only for demonstration */
			}
			
			/* Clear floats after the columns */
			.row:after {
			  content: "";
			  display: table;
			  clear: both;
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
			}else if(session.getAttribute("groupTravel") == null && session.getAttribute("privateTravel") == null){
				response.sendRedirect("home.jsp");
			}else{
				
			
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
	                      <li><a href="settings.jsp">Settings</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="logout.jsp"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
                        </ul>
                    </div>
                </div>
            
        </nav>    
        
        
	    <%
	    	
	    	if(session.getAttribute("save_private_success") != null){
	    		PrivateTravelBean privateTravelBean = (PrivateTravelBean)session.getAttribute("privateTravel");
	    		out.println("<h3 style=\"color:black\"> &nbsp;&nbsp;&nbsp;Travel to " + privateTravelBean.getDestinationBean() + " from " + privateTravelBean.getStartDateBean() + " to " + privateTravelBean.getEndDateBean() + " correctly saved! </h3> <h4 font-style:italic>&nbsp;&nbsp;&nbsp; You can book it in your manage page! <br><br>&nbsp;&nbsp;&nbsp; <a href=\"home.jsp\">Back to home!</h4></a>");			
	    		session.removeAttribute("privateTravel");
	    		session.removeAttribute("save_private_success");
	    	}
	    	else if(session.getAttribute("book_private_success") != null){
	    		PrivateTravelBean privateTravelBean = (PrivateTravelBean)session.getAttribute("privateTravel");
	    		out.println("<h3 style=\"color:black\"> &nbsp;&nbsp;&nbsp;Travel to " + privateTravelBean.getDestinationBean() + " from " + privateTravelBean.getStartDateBean() + " to " + privateTravelBean.getEndDateBean() + " correctly booked! </h3> <h4 font-style:italic>&nbsp;&nbsp;&nbsp; You can view it in your profile page! <br><br>&nbsp;&nbsp;&nbsp; <a href=\"home.jsp\">Back to home!</h4></a>");			
	    		session.removeAttribute("privateTravel");
	    		session.removeAttribute("book_private_success");
	    	}
			else if(session.getAttribute("save_public_success") != null){
				PublicTravelBean publicTravelBean = (PublicTravelBean)session.getAttribute("groupTravel");
	    		out.println("<h3 style=\"color:black\"> &nbsp;&nbsp;&nbsp;Travel to " + publicTravelBean.getDestinationBean() + " from " + publicTravelBean.getStartDateBean() + " to " + publicTravelBean.getEndDateBean() + " correctly saved! </h3> <h4 font-style:italic>&nbsp;&nbsp;&nbsp; You can book it in your manage page! <br><br>&nbsp;&nbsp;&nbsp; <a href=\"home.jsp\">Back to home!</h4></a>");				
				session.removeAttribute("groupTravel");
	    		session.removeAttribute("save_public_success");
	    	}
			else if(session.getAttribute("book_public_success") != null){
				PublicTravelBean publicTravelBean = (PublicTravelBean)session.getAttribute("groupTravel");
	    		out.println("<h3 style=\"color:black\"> &nbsp;&nbsp;&nbsp;Travel to " + publicTravelBean.getDestinationBean() + " from " + publicTravelBean.getStartDateBean() + " to " + publicTravelBean.getEndDateBean() + " correctly booked! </h3> <h4 font-style:italic>&nbsp;&nbsp;&nbsp; You can view it in your profile page! <br><br>&nbsp;&nbsp;&nbsp; <a href=\"home.jsp\">Back to home!</h4></a>");			
				session.removeAttribute("groupTravel");
	    		session.removeAttribute("book_public_success");
	    	}
	    
	    %>
    	
    	
    	<%
		}
    	%>

    </body>
</html>


		        