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
        <title>Join page</title>
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
			} else {
				
				List<PublicTravelBean> publicTravelsList = new ArrayList<>();
				JoinController joinPageController = new JoinController();
				String username = ((UserBean) session.getAttribute("user")).getUsername();
				try{
					publicTravelsList = joinPageController.allTravels(username);
				}
				catch (SystemException e){
					session.setAttribute("system_error", e.getMessage());
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
                
                	<h3><b>JOIN TO A GROUP TRAVEL AND SHARE YOUR PASSIONS</b></h3>
                	
                	<h4 style = color:orange><b>FIND THE BEST DESTIONATIONS</b></h4><br>
                	<h5 style = color:bleck><b>Select some of this filters to personalize your search</b></h5><br>
                	
                	
                	<div id="usrInf" style="background-color:#FFFFFF ">
                	    <form action="join.jsp" method="post">
                			<div class="form-group ">
						      <label class="control-label ">
						       Location
						      </label>
						      <div class="">
						       <div class="radio">
						        <label class="radio">
						         <input name="radio1" type="radio" value="Sea" />
						         Sea
						        </label>
						       </div>
						       <div class="radio">
						        <label class="radio">
						         <input name="radio1" type="radio" value="Mountain" />
						         Mountain
						        </label>
						       </div>
						      </div>
						     </div>
						     <div class="form-group ">
						      <label class="control-label ">
						      	Kind
						      </label>
						      <div class=" ">
						       <div class="checkbox">
						        <label class="checkbox">
						         <input name="checkboxArt" type="checkbox" value="Art City" />
						         Art City
						        </label>
						       </div>
						       <div class="checkbox">
						        <label class="checkbox">
						         <input name="checkboxYoung" type="checkbox" value="Young City" />
						         Young City
						        </label>
						       </div>
						      </div>
						     </div>
						     <div class="form-group ">
						      <label class="control-label " for="select">
						       Continent
						      </label>
						      <select class="select form-control" id="select" name="continent"  style=" width:150px">
						       <option value="America">
						        America
						       </option>
						       <option value="Europe">
						        Europe
						       </option>
						       <option value="Asia">
						        Asia
						       </option>
						       <option value="Africa">
						        Africa
						       </option>
						       <option value="Oceania">
						        Oceania
						       </option>
						       <option value="Not defined" selected>
						        Not defined
						       </option>
						      </select>
						      
						      </div>
						        <br>
						        
						       
		                   	   
						         <input type="HIDDEN" name="search_filtered_travels">
						         <input type="submit" value="Search" class="btn btn-warning btn-sm" style="float:left; margin-right: 5px">
						   </form>
						        
					       <form action="join.jsp" method="post">
					        	<input type="HIDDEN" name="reset_travels">
					            <input type="submit" value="Reset" class="btn btn-warning btn-sm" style="float:left">
					       </form>
						       
			          </div>
				</div>
				        
                    
                    
                
                
                <div id="trvls"  style="float: left;">
                    <h4>Joinable Travels</h4>
                    
                    <%
                    try{
	                    if(request.getParameter("send_join_request") != null){
	                   		for(PublicTravelBean publicTravelBean: publicTravelsList){
	   							if(Integer.parseInt(publicTravelBean.getIdTravelBean()) == Integer.parseInt(request.getParameter("send_join_request"))){
	   								joinPageController.sendJoinRequest(publicTravelBean, username);
	   								session.setAttribute("request_correctly_sent", "Request correctly sent!");
	   								break;
	   							}
	   						}
	                   	
	                    }
	                    else if(request.getParameter("reset_travels") != null){
	                    	publicTravelsList = joinPageController.allTravels(username);
						}
	                    else if(request.getParameter("search_filtered_travels") != null){
	                    	Boolean radioSea = false;
							Boolean radioMountain = false;
							Boolean cbArt = false;
							Boolean cbYoung = false;
							String continent = "";
							
							if(request.getParameter("radio1") != null) {
		                    	
								if(request.getParameter("radio1").equalsIgnoreCase("Sea")) {
									radioSea = true;
									radioMountain = false;
								}
								else {
									radioSea = false;
									radioMountain = true;
								}
							}
								
							if(request.getParameter("checkboxArt") != null) {
								cbArt = true;
							}
							
							if(request.getParameter("checkboxYoung") != null) {
								cbYoung = true;
							}
								
							continent = request.getParameter("continent");
														
							publicTravelsList = joinPageController.filterTravels(username, radioSea, radioMountain, cbArt, cbYoung, continent);   
	                    }
						
                    } catch (DuplicateRequestException e) {
            			session.setAttribute("duplicate_request_error", e.getMessage());
						
            		} catch (SystemException e) {
            			session.setAttribute("system_error", e.getMessage());

            		}
                    
                    if(session.getAttribute("system_error") != null) {						
                    	out.println("<h5 style=\"color:red\">" + session.getAttribute("system_error") + "</h5>");
						session.removeAttribute("system_error");
					}
                    
                    if(session.getAttribute("duplicate_request_error") != null) {
						out.println("<h5 style=\"color:red\">" + session.getAttribute("duplicate_request_error") + "</h5>");
						session.removeAttribute("duplicate_request_error");
					}
                    
                    if(session.getAttribute("request_correctly_sent") != null) {
						out.println("<h5 style=\"color:green\">" + session.getAttribute("request_correctly_sent") + "</h5>");
						session.removeAttribute("request_correctly_sent");
					}
                    %>

                    <hr>
                    
                    <%
                    	
	                    for(PublicTravelBean publicTravelBean : publicTravelsList ){
							
							String price = publicTravelBean.getHotelInfoBean().getPrice();
	                        if(price.contains("Less than"))
	                            price = "Less than &euro; 50";
	                        else if(price.contains("50") && price.contains("100"))
	                            price = "&euro; 50 - &euro; 100";
	                        else if(price.contains("100") && price.contains("150"))
	                            price = "&euro; 100 - &euro; 150";
	                        else if(price.contains("150") && price.contains("225"))
	                            price = "&euro; 150 - &euro; 225";
	                        else if(price.contains("More than"))
	                            price = "More than &euro; 225";
	                            
	                            
							out.println("<div style=\"width: 1040px; background-color:#FFEBCD\" class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a><p style= \"color: black; display:inline\"><strong> Creator: " + " </strong></p> <p style= \"color: black; display:inline\"> " + publicTravelBean.getCreatorBean() + "</p><p></p><p style= \"color: black; display: inline\"><strong>Destination: </strong></p>" + "<p style=\"color: black; display:inline\">" + publicTravelBean.getDestinationBean() + "</p><p></p><p style= \"color: black; display:inline\">" + "<strong>Description: " + "</strong></p><p style= \"color: black; display:inline\">" + publicTravelBean.getDescriptionBean() + "</p><p></p><p style= \"color: black; display:inline\">" + "<strong>Price per night: " + "</strong></p><p style= \"color: black; display:inline\"> " + price + "</p><p></p><p style= \"color: black; display:inline\"><strong> Start date: " + " </strong></p> <p style= \"color: black; display:inline\"> " + publicTravelBean.getStartDateBean() + "</p> <p style= \"color: black; display:inline\"><strong>&nbsp&nbsp&nbsp&nbspEnd date: " + " </strong></p> <p style= \"color: black; display:inline\"> " + publicTravelBean.getEndDateBean() + "</p><p></p> <p style= \"color: black; display:inline\"><strong> Number of travellers: " + " </strong></p> <p style= \"color: black; display:inline\"> " + publicTravelBean.getNumTravelersBean() + "</p><p style= \"color: black; display:inline\"><strong>&nbsp&nbsp&nbsp&nbspAvailable seats: " + " </strong></p> <p style= \"color: black; display:inline\"> " + publicTravelBean.getAvailableSeats() + "</p><p></p><p style= \"color: black; display:inline\"><strong> Hotel: " + " </strong></p> <a href=\""+ publicTravelBean.getHotelInfoBean().getHotelLink() + "\" target=\"_blank\" style= \" display:inline\"> " + publicTravelBean.getHotelInfoBean().getHotelName() + "</a><form action=\"join.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"send_join_request\" value=\""+ publicTravelBean.getIdTravelBean() +"\"><input type=\"submit\" value=\"Send join request\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form></div>");
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


