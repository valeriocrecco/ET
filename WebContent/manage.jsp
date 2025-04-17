<%@page import="logic.exceptions.DeleteGroupTravelException"%>
<%@page import="logic.exceptions.DeleteTravelException"%>
<%@page import="logic.exceptions.SystemException"%>
<%@page import="logic.exceptions.BookGroupTravelException"%>
<%@page import="logic.exceptions.BookTravelException"%>
<%@page import="java.util.List"%>
<%@page import="logic.bean.PrivateTravelBean"%>
<%@page import="logic.bean.PublicTravelBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="logic.bean.UserBean"%>
<%@page import="logic.controllers.ManagePublicTravelController"%>
<%@page import="logic.controllers.ManagePrivateTravelController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Manage</title>
        <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
                    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
       <style>
           .like-btn{
               float:right;
           }
           
           .img-logo {
               display: block;
               margin-left: 30px;
               margin-right: auto;
               height:200px;

           }

           .affix {
               top: 0;
               width: 100%;
               z-index: 9999 !important;
           }

           .affix + .container-fluid {
               padding-top: 70px;
           }
           
           /* Create two equal columns that floats next to each other */
			.column {
			  float: left;
			  width: 50%;
			  padding: 10px;
			  height: 300px; /* Should be removed. Only for demonstration */
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
			else {
				ManagePrivateTravelController managePrivateTravelController = new ManagePrivateTravelController();
				ManagePublicTravelController managePublicTravelController = new ManagePublicTravelController();

				String username = ((UserBean) session.getAttribute("user")).getUsername();
				
				List<PrivateTravelBean> privateTravelBeans = new ArrayList<>();
				List<PublicTravelBean> publicTravelBeans = new ArrayList<>();
				
				privateTravelBeans = managePrivateTravelController.loadMyUpcomingT(username);
				publicTravelBeans = managePublicTravelController.loadMyUpcomingTGR(username);
				
				try {
					if(request.getParameter("view_info_private_travel") != null) {
						// codice view info private
					}
					
					if(request.getParameter("book_private_travel") != null) {
						
		    			ManagePrivateTravelController manageTravelController = new ManagePrivateTravelController();
						
						for(PrivateTravelBean privateTravelBean: privateTravelBeans){
							if(Integer.parseInt(privateTravelBean.getIdTravelBean()) == Integer.parseInt(request.getParameter("book_private_travel"))){
								manageTravelController.bookTravel(privateTravelBean.getIdTravelBean());
								privateTravelBeans.remove(privateTravelBean);
								session.setAttribute("book_private_success", "Travel correctly booked, you can view it in your profile page!");
								session.removeAttribute("book_private_travel");
								break;
							}
						}
						 
					}
					
					if(request.getParameter("delete_private_travel") != null) {
						ManagePrivateTravelController manageTravelController = new ManagePrivateTravelController();
						
						for(PrivateTravelBean privateTravelBean: privateTravelBeans){
							if(Integer.parseInt(privateTravelBean.getIdTravelBean()) == Integer.parseInt(request.getParameter("delete_private_travel"))){
								managePrivateTravelController.deleteTravel(privateTravelBean.getIdTravelBean());
								privateTravelBeans.remove(privateTravelBean);
								session.setAttribute("delete_private_success", "Travel correctly deleted");
								session.removeAttribute("delete_private_travel");
								break;
							}
						}
					}
			
					if(request.getParameter("view_info_public_travel") != null) {
						// codice view info public
					}
					
					if(request.getParameter("book_public_travel") != null) {
						ManagePublicTravelController manageTravelController = new ManagePublicTravelController();
						
						for(PublicTravelBean publicTravelBean: publicTravelBeans){
							if(Integer.parseInt(publicTravelBean.getIdTravelBean()) == Integer.parseInt(request.getParameter("book_public_travel"))){
								manageTravelController.bookTravelGr(publicTravelBean.getIdTravelBean());
								publicTravelBeans.remove(publicTravelBean);
								session.setAttribute("book_public_success", "Travel correctly booked");
								session.removeAttribute("book_public_travel");
								break;
							}
						}
					}
					
					if(request.getParameter("delete_public_travel") != null) {
						ManagePublicTravelController manageTravelController = new ManagePublicTravelController();
						
						for(PublicTravelBean publicTravelBean: publicTravelBeans){
							if(Integer.parseInt(publicTravelBean.getIdTravelBean()) == Integer.parseInt(request.getParameter("delete_public_travel"))){
								managePublicTravelController.deleteTravelGr(publicTravelBean.getIdTravelBean());
								publicTravelBeans.remove(publicTravelBean);
								session.setAttribute("delete_public_success", "Travel correctly deleted");
								session.removeAttribute("delete_public_travel");
								break;
							}
						}
					}
					
	    		} catch (BookTravelException e) {
	    			session.setAttribute("book_travel_exception", e.getMessage());
				} catch (BookGroupTravelException e) {
					session.setAttribute("book_group_travel_exception", e.getMessage());
				} catch (DeleteTravelException e) {
					session.setAttribute("delete_private_travel_exception", e.getMessage());
				} catch (DeleteGroupTravelException e) {
					session.setAttribute("delete_public_travel_exception", e.getMessage());
				} catch (SystemException e) {
					session.setAttribute("system_error", e.getMessage());
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
        
        <div class="container-fluid text-center">
           <div class="container text-center">
           	   <%	
		           	if(session.getAttribute("book_private_success") != null) {
						out.println("<h3 style=\"color:green\">" + session.getAttribute("book_private_success") + "</h3>");
						session.removeAttribute("book_private_success");
					}
		           	if(session.getAttribute("book_public_success") != null) {
						out.println("<h3 style=\"color:green\">" + session.getAttribute("book_public_success") + "</h3>");
						session.removeAttribute("book_public_success");
					}
		           	if(session.getAttribute("delete_private_travel_exception") != null) {
						out.println("<h3 style=\"color:green\">" + session.getAttribute("delete_private_travel_exception") + "</h3>");
						session.removeAttribute("delete_private_travel_exception");
					}
		           	if(session.getAttribute("delete_public_travel_exception") != null) {
						out.println("<h3 style=\"color:green\">" + session.getAttribute("delete_public_travel_exception") + "</h3>");
						session.removeAttribute("delete_public_travel_exception");
					}
		           	if(session.getAttribute("system_error") != null) {
						out.println("<h3 style=\"color:red\">" + session.getAttribute("system_error") + "</h3>");
						session.removeAttribute("system_error");
					}
		           	if(session.getAttribute("book_travel_exception") != null) {
						out.println("<h3 style=\"color:red\">" + session.getAttribute("book_travel_exception") + "</h3>");
						session.removeAttribute("book_travel_exception");
					}
		           	if(session.getAttribute("book_group_travel_exception") != null) {
						out.println("<h3 style=\"color:red\">" + session.getAttribute("book_group_travel_exception") + "</h3>");
						session.removeAttribute("book_group_travel_exception");
					}
           	   %>
               <div class="row">
               
               	   <% out.println("<h3 style=\"color:orange\"><strong>MANAGE YOUR UPCOMING TRAVELS</strong></h3>");%>
               	   
                   <div id="privateTravels" class="column">
						<%		
							out.println("<h4>Private travels</h4>");
							String price = "";
							for(PrivateTravelBean privateTravelBean : privateTravelBeans) { 
								price = privateTravelBean.getHotelInfoBean().getPrice();
								price = price.replaceAll("euro", "&euro;");
								out.println("<div style=\"background-color:#FFEBCD\" class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a><p style= \"color: black; display:inline\"><strong> Travel name: " + " </strong></p> <p style= \"color: black; display:inline\"> " + privateTravelBean.getTravelNameBean() + "</p><p></p><p style= \"color: black; display: inline\"><strong>Destination: </strong></p>" + "<p style=\"color: black; display:inline\">" + privateTravelBean.getDestinationBean() + "</p><p></p><p style= \"color: black; display:inline\">" + "<strong>Description: " + "</strong></p><p style= \"color: black; display:inline\">" + privateTravelBean.getDescriptionBean() + "</p><p></p><p style= \"color: black; display:inline\"><strong> Price per night: " + " </strong></p> <p style= \"color: black; display:inline\"> " + price + "</p><p></p><p style= \"color: black; display:inline\"><strong> Start date: " + " </strong></p> <p style= \"color: black; display:inline\"> " + privateTravelBean.getStartDateBean() + "</p> <p style= \"color: black; display:inline\"><strong>&nbsp&nbsp&nbsp&nbspEnd date: " + " </strong></p> <p style= \"color: black; display:inline\"> " + privateTravelBean.getEndDateBean() + "</p><p></p><form action=\"manage.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"book_private_travel\" value=\""+ privateTravelBean.getIdTravelBean() +"\"><input type=\"submit\" value=\"Book\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form><form action=\"manage.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"delete_private_travel\" value=\""+ privateTravelBean.getIdTravelBean() +"\"><input type=\"submit\" value=\"Delete\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form></div>");
							}
						%>
                       <hr>
                   </div>
                   <div id="publicTravels" class="column">
		       			<%		
		       				out.println("<h4>Public travels</h4>");	
			       			
		       				for(PublicTravelBean publicTravelBean : publicTravelBeans) {
		       					price = publicTravelBean.getHotelInfoBean().getPrice();
								price = price.replaceAll("euro", "&euro;");
								out.println("<div style=\"background-color:#FFEBCD\" class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a><p style= \"color: black; display:inline\"><strong> Travel name: " + " </strong></p> <p style= \"color: black; display:inline\"> " + publicTravelBean.getTravelNameBean() + "</p><p></p><p style= \"color: black; display: inline\"><strong>Destination: </strong></p>" + "<p style=\"color: black; display:inline\">" + publicTravelBean.getDestinationBean() + "</p><p></p><p style= \"color: black; display:inline\">" + "<strong>Description: " + "</strong></p><p style= \"color: black; display:inline\">" + publicTravelBean.getDescriptionBean() + "</p><p></p><p style= \"color: black; display:inline\"><strong> Price per night: " + " </strong></p> <p style= \"color: black; display:inline\"> " + price + "</p><p></p><p style= \"color: black; display:inline\"><strong> Start date: " + " </strong></p> <p style= \"color: black; display:inline\"> " + publicTravelBean.getStartDateBean() + "</p> <p style= \"color: black; display:inline\"><strong>&nbsp&nbsp&nbsp&nbspEnd date: " + " </strong></p> <p style= \"color: black; display:inline\"> " + publicTravelBean.getEndDateBean() + "</p><p></p><form action=\"manage.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"book_public_travel\" value=\""+ publicTravelBean.getIdTravelBean() +"\"><input type=\"submit\" value=\"Book\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form><form action=\"manage.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"delete_public_travel\" value=\""+ publicTravelBean.getIdTravelBean() +"\"><input type=\"submit\" value=\"Delete\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form></div>");
		       				}
						%>
                       <hr>
                   </div>
                   
               </div>
           </div>
        </div>
        <%
        	}
        %>
    </body>
</html>


