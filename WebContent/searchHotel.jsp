<%@page import="logic.bean.PrivateTravelBean"%>
<%@page import="logic.bean.HotelBean"%>
<%@page import="sun.net.idn.Punycode"%>
<%@page import="logic.exceptions.DuplicateRequestException"%>
<%@page import="logic.exceptions.SystemException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="logic.bean.UserBean"%>
<%@page import="logic.bean.PublicTravelBean"%>
<%@page import="logic.controllers.JoinController"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Search hotel</title>
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
           }
       
           .affix {
               top: 0;
               width: 100%;
               z-index: 9999 !important;
           }
       
       
           .affix + .container-fluid {
               padding-top: 70px;
           }
       
       
       
           li a:hover:not(.active) {
               background-color: #ff0000;
               color: white;
           }
           
           .center {
			  text-align: center;
			}
       
       		.img-usr{
       			height: 55px;
       			width: 55px;
       		}
       		
       		a:visited {
		        color: #095484;
		      }
       		
       		a:hover{
       			
       		  	color:#8ebf42;
       		
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
				ArrayList<HotelBean> hotels = (ArrayList<HotelBean>) session.getAttribute("hotels"); 
	      	  	
				PublicTravelBean publicTravelBean = null;
				PrivateTravelBean privateTravelBean = null;
				
				if(session.getAttribute("groupTravel") != null){
					publicTravelBean = (PublicTravelBean)session.getAttribute("groupTravel");
				}
				
				if(session.getAttribute("privateTravel") != null){
					privateTravelBean = (PrivateTravelBean)session.getAttribute("privateTravel");
				}

				if(session.getAttribute("groupTravel") == null && session.getAttribute("privateTravel") == null){
					response.sendRedirect("plan.jsp");
				}
				else if(request.getParameter("summary_plan") != null && session.getAttribute("groupTravel") != null){
					for(HotelBean hBean: hotels){
						if(hBean.getHotelName().equalsIgnoreCase(request.getParameter("summary_plan"))){
							publicTravelBean.getHotelInfoBean().setHotelName(hBean.getHotelName());
							publicTravelBean.getHotelInfoBean().setHotelLink(hBean.getHotelLink());
							session.setAttribute("groupTravel", publicTravelBean);
						}
					}
					session.removeAttribute("summary_plan");
					response.sendRedirect("summaryPublicPlan.jsp");
				}
				else if(request.getParameter("summary_plan") != null && session.getAttribute("privateTravel") != null){
					for(HotelBean hBean: hotels){
						if(hBean.getHotelName().equalsIgnoreCase(request.getParameter("summary_plan"))){
							privateTravelBean.getHotelInfoBean().setHotelName(hBean.getHotelName());
							privateTravelBean.getHotelInfoBean().setHotelLink(hBean.getHotelLink());
							session.setAttribute("privateTravel", privateTravelBean);
						}
					}
					session.removeAttribute("summary_plan");
					response.sendRedirect("summaryPrivatePlan.jsp");
				}

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
                          <li><a href="logout.jsp"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                      </ul>
                  </div>
            </div>
        </nav>
        
        <div class="container-fluid">
            <div class="row content">
                <div class="col-sm-3 sidenav" style="float:left; border-right: 1px solid #000000; margin:5px">
                
                	<h3 style= color:orange><b>TRAVEL INFO</b></h3>
                	
                	<div id="usrInf" style="background-color:#FFFFFF ">
                	    <form action="join.jsp" method="post">
                		<div class="form-group ">
                			<div class="form-group ">
						      <label class="control-label " for="destination">
						       Travel Name
						      </label>
						      <% 
						      
						      if(publicTravelBean != null){
						    	  out.println("<input class=\"form-control\" id=\"travelName\" name=\"travelName\" value=\""+ publicTravelBean.getTravelNameBean() +"\" type= \"text\" readonly>");
						      }
						      else{
						    	  out.println("<input class=\"form-control\" id=\"travelName\" name=\"travelName\" value=\""+  privateTravelBean.getTravelNameBean() +"\" type= \"text\" readonly>");
							  }
						      
						      %>
						    </div> 
						    <div class="form-group ">
						      <label class="control-label " for="destination">
						       Destination
						      </label>
						      <% 
						      
						      if(publicTravelBean != null){
						    	  out.println("<input class=\"form-control\" id=\"destination\" name=\"destination\" value=\""+ publicTravelBean.getDestinationBean() +"\" type= \"text\" readonly>");
						      }
						      else{
						    	  out.println("<input class=\"form-control\" id=\"destination\" name=\"destination\" value=\""+  privateTravelBean.getDestinationBean() +"\" type= \"text\" readonly>");
							  }
						      
						      %>
						    </div> 
						    
						    <div class="form-group ">
						      <label class="control-label " for="stars">
						       Stars
						      </label>
						      <% 
						      
						      if(publicTravelBean != null){
						    	  out.println("<input class=\"form-control\" id=\"stars\" name=\"stars\" value=\""+ publicTravelBean.getHotelInfoBean().getStars() +"\" type= \"text\" readonly>");
						      }
						      else{
						    	  out.println("<input class=\"form-control\" id=\"stars\" name=\"stars\" value=\""+  privateTravelBean.getHotelInfoBean().getStars() +"\" type= \"text\" readonly>");
							  }
						      
						      %>
						    </div>  
						    
						    <div class="form-group ">
						      <label class="control-label " for="Price">
						       Price per night
						      </label>
						      <% 
						      
						      if(publicTravelBean != null){
						    	  out.println("<input class=\"form-control\" id=\"price\" name=\"price\" value=\""+ publicTravelBean.getHotelInfoBean().getPrice() +"\" type= \"text\" readonly>");
						      }
						      else{
						    	  out.println("<input class=\"form-control\" id=\"price\" name=\"price\" value=\""+  privateTravelBean.getHotelInfoBean().getPrice() +"\" type= \"text\" readonly>");
							  }
						      
						      %>
						    </div> 
						    
						    <div class="form-group ">
						      <label class="control-label " for="breakfast">
						       Breakfast
						      </label>
						      <% 
						      
						      if(publicTravelBean != null){
						    	  out.println("<input class=\"form-control\" id=\"breakfast\" name=\"breakfast\" value=\""+ publicTravelBean.getHotelInfoBean().getBreakfast() +"\" type= \"text\" readonly>");
						      }
						      else{
						    	  out.println("<input class=\"form-control\" id=\"breakfast\" name=\"breakfast\" value=\""+  privateTravelBean.getHotelInfoBean().getBreakfast() +"\" type= \"text\" readonly>");
							  }
						      
						      %>
						    </div> 
						    
						    <div class="form-group ">
						      <label class="control-label " for="start_date">
						       Start date
						      </label>
						      <% 
						      
						      if(publicTravelBean != null){
						    	  out.println("<input class=\"form-control\" id=\"start_date\" name=\"start_date\" value=\""+ publicTravelBean.getStartDateBean() +"\" type= \"text\" readonly>");
						      }
						      else{
						    	  out.println("<input class=\"form-control\" id=\"start_date\" name=\"start_date\" value=\""+  privateTravelBean.getStartDateBean() +"\" type= \"text\" readonly>");
							  }
						      
						      %>
						    </div> 
						    
						    <div class="form-group ">
						      <label class="control-label " for="end_date">
						       End date
						      </label>
						      <% 
						      
						      if(publicTravelBean != null){
						    	  out.println("<input class=\"form-control\" id=\"end_date\" name=\"end_date\" value=\""+ publicTravelBean.getEndDateBean() +"\" type= \"text\" readonly>");
						      }
						      else{
						    	  out.println("<input class=\"form-control\" id=\"end_date\" name=\"end_date\" value=\""+  privateTravelBean.getEndDateBean() +"\" type= \"text\" readonly>");
							  }
						      
						      %>
						    </div> 
						    
						    <div class="form-group ">
						      <label class="control-label " for="travellers">
						       Travelers
						      </label>
						      <% 
						      
						      if(publicTravelBean != null){
						    	  out.println("<input class=\"form-control\" id=\"travellers\" name=\"travellers\" value=\""+ publicTravelBean.getNumTravelersBean() +"\" type= \"text\" readonly>");
						      }
						      else{
						    	  out.println("<input class=\"form-control\" id=\"travellers\" name=\"travellers\" value=\""+  privateTravelBean.getNumTravelersBean() +"\" type= \"text\" readonly>");
							  }
						      
						      %>
						    </div> 
						    
						    <div class="form-group ">
						      <label class="control-label " for="rooms">
						       Rooms
						      </label>
						      <% 
						      
						      if(publicTravelBean != null){
						    	  out.println("<input class=\"form-control\" id=\"rooms\" name=\"rooms\" value=\""+ publicTravelBean.getHotelInfoBean().getNumRooms() +"\" type= \"text\" readonly>");
						      }
						      else{
						    	  out.println("<input class=\"form-control\" id=\"rooms\" name=\"rooms\" value=\""+  privateTravelBean.getHotelInfoBean().getNumRooms() +"\" type= \"text\" readonly>");
							  }
						      
						      %>
						    </div>
				        </div>
				        </form>
                    </div>
                </div>  

                <div id="trvls"  style="float: left;">
                    <h4>Hotels</h4>
                    
                    <%
                    	
	                    if(session.getAttribute("system_error") != null) {
							out.println("<h5 style=\"color:red\">" + session.getAttribute("system_error") + "</h5>");
							session.removeAttribute("system_error");
						}
	                   
	                    if(hotels.isEmpty()){
							out.println("<h4 style=\"color:black\">Hotels not found</h4>");

	                    }
                    %>
                   
                    
                    <hr>
                    
                    <%
                    
                    
              	  	for (HotelBean hotel: hotels) {   
						out.println("<div style= \"width: 1040px; background-color:#FFEBCD;\" class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a><p style= \"color: black; display:inline\"><strong> Hotel: " + " </strong></p> <a href=\""+ hotel.getHotelLink() + "\" target=\"_blank\" style= \" display:inline\"> " + hotel.getHotelName() + "</a><form action=\"searchHotel.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"summary_plan\" value=\""+ hotel.getHotelName() +"\"><input type=\"submit\" value=\"Select hotel\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form></div>");
					}
              	  
	            		
					%>
					
                </div>
            </div>
        </div>
        
        <%
			}
        %>
       
    </body>
</html>