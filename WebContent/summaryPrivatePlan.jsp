<%@page import="logic.exceptions.DescriptionSyntaxException"%>
<%@page import="logic.exceptions.BookTravelException"%>
<%@page import="logic.exceptions.SaveTravelException"%>
<%@page import="logic.exceptions.SystemException"%>
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
        <title>Summary Private Plan</title>
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
			}
			else if(session.getAttribute("privateTravel") == null){
				response.sendRedirect("plan.jsp");
			}
			else{
				PrivateTravelBean privateTravelBean = null;
				
				if(session.getAttribute("privateTravel") != null){
					privateTravelBean = (PrivateTravelBean)session.getAttribute("privateTravel");
				}
			
		%>
    	
    	
    	
        
        <div>
            <div class="container-fluid" style="background-color:#222">
                <img class="img-logo" src="Images/logoET.png">
            </div>
            
        </div>
        
        
        <nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="197">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div>
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
                            <li><a href="logout.jsp"><span class="glyphicon glyphicon-user"></span> Logout</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </nav>    
        
        <div class ="img-group-sett container-fluid" style="border: 1px solid #000000; margin:5px">
        	
        	<form action="summaryPrivatePlan.jsp" method="post">
        	    
		        <div style="float:left; width:400px; background-color:#FFF; padding:10px;">
		        
		        	<div class="form-group ">
				      <label class="control-label" for="creator">
				       Creator
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"creator\" name=\"creator\" value=\""+ privateTravelBean.getCreatorBean() +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>
				    </div>
				    
		            <div class="form-group ">
				      <label class="control-label requiredField" for="name">
				       Travel Name
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"name\" name=\"name\" value=\""+ privateTravelBean.getTravelNameBean() +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>
				    </div>
				    
				    <div class="form-group ">
				      <label class="control-label " for="dest">
				       Destination
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"dest\" name=\"dest\" value=\""+ privateTravelBean.getDestinationBean() +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>
				    </div>
					
					<div class="form-group ">
				      <label class="control-label " for="startDate">
				       Start date
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"startDate\" name=\"startDate\" value=\""+ privateTravelBean.getStartDateBean() +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>				      
				    </div>
				    
				    <div class="form-group ">
				      <label class="control-label " for="endDate">
				       End date
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"endDate\" name=\"endDate\" value=\""+ privateTravelBean.getEndDateBean() +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>
				    </div>
				     
				</div>

	        
		        <div style="float:left;width:400px; background-color:#FFF; padding:10px;">
					 <div class="form-group ">
				      <label class="control-label " for="hotel">
				       Hotel name
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"hotel\" name=\"hotel\" value=\""+ privateTravelBean.getHotelInfoBean().getHotelName() +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>
				    </div>
				    
				    <div class="form-group ">
				      <label class="control-label " for="stars">
				       Stars
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"stars\" name=\"stars\" value=\""+ privateTravelBean.getHotelInfoBean().getStars() +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>
				    </div>
				    
				    <div class="form-group ">
				      <label class="control-label " for="rooms">
				       Rooms
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"rooms\" name=\"rooms\" value=\""+ privateTravelBean.getHotelInfoBean().getNumRooms() +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>
				    </div>
				    
				    <div class="form-group ">
				      <label class="control-label " for="breakfast">
				       Breakfast
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"breakfast\" name=\"breakfast\" value=\""+ privateTravelBean.getHotelInfoBean().getBreakfast() +"\" type= \"text\" readonly>");
				      }
				      
				      %>
				    </div>
				    
				    <div class="form-group ">
				      <label class="control-label " for="price">
				       Price per night
				      </label>
				      <% 
						      
				      if(privateTravelBean != null) {
				    	  String price = privateTravelBean.getHotelInfoBean().getPrice();
				    	  price = price.replaceAll("euro", "&euro;");
				    	  out.println("<input class=\"form-control\" id=\"price\" name=\"price\" value=\""+ price +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>
				    </div>
				    		            

				</div>
		        
		        <div style="float:left;width:400px; background-color:#FFF;  padding:10px;">
		            <div class="form-group ">
				      <label class="control-label " for="number">
				       Number of Travellers
				      </label>
				      <% 
						      
				      if(privateTravelBean != null){
				    	  out.println("<input class=\"form-control\" id=\"numberOfTravellers\" name=\"numberOfTravellers\" value=\""+ privateTravelBean.getNumTravelersBean() +"\" type= \"text\" readonly>");
				      }
				      
				      
				      %>
				     </div>
				    
				    <form action="summaryPrivatePlan.jsp" method="post">
				    	<div class="form-group">
					     <label class="control-label " for="description">
					       Description:
					     </label>
						 <textarea id="description" name="description" rows="4" cols="50" required>
						 </textarea>
							    
					    </div>
					    <div>
					    	<input type="submit" name="summary_save_private" value="Save" class="btn btn-warning btn-sm" style="float:left; margin-right: 5px;">
				        	<input type="submit" name="summary_book_private" value="Book" class="btn btn-warning btn-sm" style="float:left">
					    </div>
			        	
			        </form>
		        </div>

	        </form>
	        <hr>
	        
	        
   
    	</div>
    	
    	
        <%
        
	        PlanController planController = new PlanController();
	        
	        try{
	        	
	        	if(request.getParameter("summary_save_private") != null){
	        		System.out.println("HEREEEE SAVEE");
	        		privateTravelBean.setAndValidateDescription(request.getParameter("description"));
	        		session.setAttribute("privateTravel", privateTravelBean);
	    	        planController.saveTravel(privateTravelBean);
					session.setAttribute("save_private_success", "Travel correctly saved!");
				}
	        	else if(request.getParameter("summary_book_private") != null){
		        	privateTravelBean.setAndValidateDescription(request.getParameter("description"));
		        	session.setAttribute("privateTravel", privateTravelBean);
					planController.bookAndSaveTravel(privateTravelBean);
					session.setAttribute("book_private_success", "Travel correctly booked!");
				}
		        
	        } catch(SystemException e){
	        	session.setAttribute("system_exception", e.getMessage());
	        } catch(SaveTravelException e){
	        	session.setAttribute("save_travel_exception", e.getMessage());
	        } catch(DatesException e){
	        	session.setAttribute("dates_exception", e.getMessage());
	        } catch(TravRoomException e){
	        	session.setAttribute("trav_room_exception", e.getMessage());
	        } catch(BookTravelException e){
	        	session.setAttribute("book_travel_exception", e.getMessage());
	        } catch(DescriptionSyntaxException e){
	        	session.setAttribute("description_exception", e.getMessage());
	        }
			
	        
	        if(session.getAttribute("system_exception") != null){
				out.println("<h5 style=\"color:red\">" + session.getAttribute("system_error") + "</h5>");
	        	session.removeAttribute("system_exception");
	        }
	        
			if(session.getAttribute("dates_exception") != null){
				out.println("<h5 style=\"color:red\">" + session.getAttribute("dates_exception") + "</h5>");
        		session.removeAttribute("dates_exception");
	        }
				
			if(session.getAttribute("save_travel_exception") != null){
				out.println("<h5 style=\"color:red\">" + session.getAttribute("save_travel_exception") + "</h5>");
	        	session.removeAttribute("save_travel_exception");
	        }
			
			if(session.getAttribute("trav_room_exception") != null){
				out.println("<h5 style=\"color:red\">" + session.getAttribute("trav_room_exception") + "</h5>");
	        	session.removeAttribute("trav_room_exception");
	        }
			
			if(session.getAttribute("book_travel_exception") != null){
				out.println("<h5 style=\"color:red\">" + session.getAttribute("book_travel_exception") + "</h5>");
	        	session.removeAttribute("book_travel_exception");
	        }
			
			if(session.getAttribute("description_exception") != null){
				out.println("<h5 style=\"color:red\">" + session.getAttribute("description_exception") + "</h5>");
	        	session.removeAttribute("description_exception");
	        }
			
			if(session.getAttribute("book_private_success") != null){
				response.sendRedirect("planSuccess.jsp");
	        }
			
			if(session.getAttribute("save_private_success") != null){
				response.sendRedirect("planSuccess.jsp");
	        }
        
        %>
    	
    	
    	<%
		}
    	%>

    </body>
</html>


		        