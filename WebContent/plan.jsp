<%@page import="java.util.Random"%>
<%@page import="logic.bean.DestinationBean"%>
<%@page import="java.util.ArrayList"%>
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
        <title>Create Travel</title>
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
			}else{
				
				session.removeAttribute("groupTravel");
				session.removeAttribute("privateTravel");
				session.removeAttribute("hotels");
			
		%>
    	
    	<script>
    	jQuery(document).ready(function($){
    	    
    		$(".btnrating").on('click',(function(e) {
    		
    		var previous_value = $("#selected_rating").val();
    		
    		var selected_value = $(this).attr("data-attr");
    		$("#selected_rating").val(selected_value);
    		
    		$(".selected-rating").empty();
    		$(".selected-rating").html(selected_value);
    		
    		for (i = 1; i <= selected_value; ++i) {
    		$("#rating-star-"+i).toggleClass('btn-warning');
    		$("#rating-star-"+i).toggleClass('btn-default');
    		}
    		
    		for (j = 1; j <= previous_value; ++j) {
    		$("#rating-star-"+j).toggleClass('btn-warning');
    		$("#rating-star-"+j).toggleClass('btn-default');
    		}
    		
    		}));
    		
    			
    	});

    	</script>
    	
        
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
                            <li><a href="logout.jsp"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </nav>   
        
        <h3 style="color:orange; margin-left: 10px"><b>   PLAN YOUR TRAVEL</b></h3>
              
        
        <div class ="img-group-sett container-fluid" style="border: 1px solid #000000; margin:5px">
        	   	
        	<form action="plan.jsp" method="post">
        	    
		        <div style="float:left; width:400px; background-color:#FFF; padding:10px;">
		            <div class="form-group ">
				      <label class="control-label requiredField" for="name">
				       Travel Name
				       
				      </label>
				      <input class="form-control" id="name" name="name" type="text" minlength="3" maxlength="20" placeholder="Travel name" required/>
				    </div>

				    <div class="form-group ">
				      <label class="control-label requiredField">
				       Kind of travel
				       
				      </label>
				      <div class="">
				       <div class="radio">
				        <label class="radio">
				         <input name="radio" type="radio" value="Private" checked="checked" required/>
				         Private
				        </label>
				       </div>
				       <div class="radio">
				        <label class="radio">
				         <input name="radio" type="radio" value="Public" required/>
				         Public
				        </label>
				       </div>
				      </div>
				    </div>

				    <div class="form-group">
				      <label class="control-label " for="date">
				       From
				      </label>
				      <div class="input-group">
				       <input class="form-control" id="date" name="dateStart" placeholder="MM/DD/YYYY" type="date" required/>
				       <div class="input-group-addon">
				        <i class="fa fa-calendar">
				        </i>
				       </div>
				      </div>
				     </div>
				     <div class="form-group ">
				      <label class="control-label " for="date1">
				       To
				      </label>
				      <div class="input-group">
				       <input class="form-control" id="date1" name="dateEnd" placeholder="MM/DD/YYYY" type="date" required/>
				       <div class="input-group-addon">
				        <i class="fa fa-calendar">
				        </i>
				       </div>
				      </div>
				      <span class="help-block" id="hint_date1">
				      </span>
				    </div>
				</div>

	        
		        <div style="float:left;width:400px; background-color:#FFF; padding:10px;">
		            <div class="form-group ">
				      <label class="control-label " for="dest">
				       Destination
				      </label>
				      <%
				      	if(session.getAttribute("destination") != null) {
				      		out.println("<input class=\"form-control\" id=\"dest\" name=\"dest\" value=\"" + ((DestinationBean)session.getAttribute("destination")).getDestinationName() + "\" type=\"text\" required/>");
				      		session.removeAttribute("destination");
				      	}
				      	else {
				      		out.println("<input class=\"form-control\" id=\"dest\" name=\"dest\" placeholder=\"Destination\" type=\"text\" required/>");
				      		session.removeAttribute("destination");
				      	}
				      %>
				     
				    </div>
		        </div>
		        
		        <div style="float:left;width:400px; background-color:#FFF;  padding:10px;">
		            <div class="form-group ">
				      <label class="control-label " for="number">
				       Number of Travelers
				      </label>
				      <input class="form-control" id="number" name="numberOfTravellers" type="number" min="1" max="50" placeholder="Number of travelers" required/>
				     </div>
				     <div class="form-group ">
				      <label class="control-label " for="number1">
				       Number of rooms
				      </label>
				      <input class="form-control" id="number1" name="numberOfRooms" type="number" min="1" max="25" placeholder="Number of rooms" required/>
				     </div>
				     <div class="form-group ">
				      <label class="control-label ">
				      </label>
				      <div class=" ">
				       <div class="checkbox">
				        <label class="checkbox">
				         <input name="cbBreakfast" type="checkbox" value="isSelected"/>
				         Breakfast included
				        </label>
				       </div>
				      </div>
				     </div>
				    
				    <div class="form-group" id="rating-ability-wrapper">
				    
				    	<label class="control-label" for="rating">
					    <span class="field-label-header">Hotel stars rating</span><br>
					    <span class="field-label-info"></span>
					    <input type="hidden" id="selected_rating" name="selectedRating" value="3" required="required">
					    </label>
					    <h2 class="bold rating-header" style="">
					    <span class="selected-rating">3</span><small> / 5</small>
					    </h2>
					    
					    <button type="button" class="btnrating btn btn-warning btn-lg" data-attr="1" id="rating-star-1">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
					    <button type="button" class="btnrating btn btn-warning btn-lg" data-attr="2" id="rating-star-2">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
					    <button type="button" class="btnrating btn btn-warning btn-lg" data-attr="3" id="rating-star-3">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
					    <button type="button" class="btnrating btn btn-default btn-lg" data-attr="4" id="rating-star-4">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
					    <button type="button" class="btnrating btn btn-default btn-lg" data-attr="5" id="rating-star-5">
					        <i class="fa fa-star" aria-hidden="true"></i>
					    </button>
				    </div>

				    <div class="form-group ">
				      <label class="control-label " for="select">
				       Price per night
				      </label>
				      <select class="select form-control" id="select" name="price">
				       <option value="Not defined" selected>
				        Not defined
				       </option>
				       <option value="Less than euro 50">
				        Less than &euro; 50
				       </option>
				       <option value="euro 50 - euro 100">
				        &euro; 50 - &euro; 100
				       </option>
				       <option value="euro 100 - euro 150">
				        &euro; 100 - &euro; 150
				       </option>
				       <option value="euro 150 - euro 225">
				        &euro; 150 - &euro; 225 
				       </option>
				       <option value="More than euro 225">
				        More than &euro; 225 
				       </option>
				      
				      </select>
				    </div>
				    
				    <div class="form-group">
				     
				      <div>
				      
				       <% 
				       
				       if(request.getParameter("radio") != null){
				        	
				    	   	List<HotelBean> listHotelsBean = new ArrayList<>(); 
				        	PlanController planController = new PlanController();
					        UserBean userBean = (UserBean)session.getAttribute("user");
				        	try{
				        		
								if(request.getParameter("radio").equalsIgnoreCase("Public")) {
									int availableSeats = 0;
									PublicTravelBean viaggioGruppoBean = new PublicTravelBean();
									HotelBean hotelBean = new HotelBean();
									viaggioGruppoBean.setHotelInfoBean(hotelBean);
									
									if(request.getParameter("name") != null){
										viaggioGruppoBean.setAndValidateTravelName(request.getParameter("name"));
									}
									
									if(request.getParameter("cbBreakfast") != null) {
										viaggioGruppoBean.getHotelInfoBean().setBreakfast("Included");
									}
									else {
										viaggioGruppoBean.getHotelInfoBean().setBreakfast("Not included");
									}
									
									if(request.getParameter("dest") != null){
										viaggioGruppoBean.setAndValidateDestination(request.getParameter("dest"));
									}
									
									if(request.getParameter("dateStart") != null && request.getParameter("dateEnd") != null){
										viaggioGruppoBean.setStartDateBean(request.getParameter("dateStart"));
										viaggioGruppoBean.setEndDateBean(request.getParameter("dateEnd"));
										planController.validateDates(request.getParameter("dateStart"), request.getParameter("dateEnd"));
									}
									
									if(request.getParameter("numberOfTravellers") != null){
										viaggioGruppoBean.setAndValidateNumTravelers(request.getParameter("numberOfTravellers"));
										availableSeats = Integer.parseInt(request.getParameter("numberOfTravellers")) -1;
										viaggioGruppoBean.setAvailableSeats(String.valueOf(availableSeats));
									}
									
									if(request.getParameter("numberOfRooms") != null){
										viaggioGruppoBean.getHotelInfoBean().setAndValidateNumRooms(request.getParameter("numberOfRooms"));
									}
									
									viaggioGruppoBean.getHotelInfoBean().setPrice(request.getParameter("price"));

									viaggioGruppoBean.getHotelInfoBean().setStars(request.getParameter("selectedRating"));
									
									viaggioGruppoBean.setCreatorBean(userBean.getUsername());
									
									planController.validateTravelersAndRooms(viaggioGruppoBean.getNumTravelersBean(), viaggioGruppoBean.getHotelInfoBean().getNumRooms());
		
									session.setAttribute("groupTravel", viaggioGruppoBean);
								
									listHotelsBean = planController.searchHotels(viaggioGruppoBean.getDestinationBean(), viaggioGruppoBean.getStartDateBean(), viaggioGruppoBean.getEndDateBean(), viaggioGruppoBean.getNumTravelersBean(), viaggioGruppoBean.getHotelInfoBean());
									
									session.setAttribute("hotels", listHotelsBean);
									response.sendRedirect("searchHotel.jsp");
								
								}
								else {
								
									PrivateTravelBean viaggioBean = new PrivateTravelBean();
									HotelBean hotelBean = new HotelBean();
									viaggioBean.setHotelInfoBean(hotelBean);
									
									if(request.getParameter("cbBreakfast") != null) {
										viaggioBean.getHotelInfoBean().setBreakfast("Included");
									}
									else {
										viaggioBean.getHotelInfoBean().setBreakfast("Not included");									
									}
									
									if(request.getParameter("dest") != null){
										viaggioBean.setAndValidateDestination(request.getParameter("dest"));									}
									
									if(request.getParameter("dateStart") != null && request.getParameter("dateEnd") != null){
										viaggioBean.setStartDateBean(request.getParameter("dateStart"));
										viaggioBean.setEndDateBean(request.getParameter("dateEnd"));
										planController.validateDates(request.getParameter("dateStart"), request.getParameter("dateEnd"));
									}
									if(request.getParameter("name") != null){
										viaggioBean.setAndValidateTravelName(request.getParameter("name"));
									}
									
									if(request.getParameter("numberOfTravellers") != null){
										viaggioBean.setAndValidateNumTravelers(request.getParameter("numberOfTravellers"));
									}
									
									if(request.getParameter("numberOfRooms") != null){
										viaggioBean.getHotelInfoBean().setAndValidateNumRooms(request.getParameter("numberOfRooms"));
									}
									
									viaggioBean.getHotelInfoBean().setPrice(request.getParameter("price"));

									viaggioBean.getHotelInfoBean().setStars(request.getParameter("selectedRating"));

									viaggioBean.setCreatorBean(userBean.getUsername());
									
									planController.validateTravelersAndRooms(viaggioBean.getNumTravelersBean(), viaggioBean.getHotelInfoBean().getNumRooms());
									
									session.setAttribute("privateTravel", viaggioBean);
									
									listHotelsBean = planController.searchHotels(viaggioBean.getDestinationBean(), viaggioBean.getStartDateBean(), viaggioBean.getEndDateBean(), viaggioBean.getNumTravelersBean(), viaggioBean.getHotelInfoBean());

									session.setAttribute("hotels", listHotelsBean);
									response.sendRedirect("searchHotel.jsp");
								}
				        		
				        	} catch (IOException e) {
								e.printStackTrace();
							} catch (TravelNameSyntaxException e) {
								session.setAttribute("travel_name_syntax_error", e.getMessage());
							} catch (DestinationSyntaxException e) {
								session.setAttribute("destination_syntax_error", e.getMessage());
							} catch (NumTravSyntaxException e) {
								session.setAttribute("number_of_travellers_syntax_error", e.getMessage());
							} catch (NumRoomsSyntaxException e) {
								session.setAttribute("number_of_rooms_syntax_error", e.getMessage());
							} catch (DatesException e) {
								session.setAttribute("dates_error", e.getMessage());
							} catch (TravRoomException e) {
								session.setAttribute("trav_rooms_error", e.getMessage());
							} 
							
				        }
				       
					       if(session.getAttribute("travel_name_syntax_error") != null){
						        out.println("<p style=\"color:red\">" + session.getAttribute("travel_name_syntax_error") + "</p>");
								session.removeAttribute("travel_name_syntax_error");
						    }
								
							if(session.getAttribute("destination_syntax_error") != null){
								out.println("<p style=\"color:red\">" + session.getAttribute("destination_syntax_error") + "</p>");
								session.removeAttribute("destination_syntax_error");
							}
								
							if(session.getAttribute("number_of_travellers_syntax_error") != null){
								out.println("<p style=\"color:red\">" + session.getAttribute("number_of_travellers_syntax_error") + "</p>");
								session.removeAttribute("number_of_travellers_syntax_error");
							} 
								
							if(session.getAttribute("number_of_rooms_syntax_error") != null){
								out.println("<p style=\"color:red\">" + session.getAttribute("number_of_rooms_syntax_error") + "</p>");
								session.removeAttribute("number_of_rooms_syntax_error");
							} 
								
							if(session.getAttribute("dates_error") != null) {
								out.println("<p style=\"color:red\">" + session.getAttribute("dates_error") + "</p>");
								session.removeAttribute("dates_error");
							}
								
							
							if(session.getAttribute("trav_rooms_error") != null) {
								out.println("<p style=\"color:red\">" + session.getAttribute("trav_rooms_error") + "</p>"); 
								session.removeAttribute("trav_rooms_error");
							}
					      
				       %>
				       <input class="btn btn-warning" name="submit" type="submit" value="Search hotel" />
				        
				      </div>
				    </div>
		        </div>
	        </form>
	        
   			<form action="plan.jsp" method="post">
				    <div class="form-group" style="width:300px; float:left; border: 1px solid #000; margin-top:20px; margin-left: 10px; padding:10px;">
				   	  <p><h4><b>If you need some help to choose the best destination for you, you can use these <i style="color:orange">filters!</i></b></h4></p>
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
				      <select class="select form-control" id="select" name="continent">
				      <option value="Not defined">
				        Not defined
				       </option>
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
				      </select>
				     
					      <input type="HIDDEN" name="search_destinations">
					      <input class="btn btn-warning" name="submit" type="submit" value="Search" style = "margin-top: 10px;"/>
				      
				    </div>
				    </div>
		        </form>
		        
		     <%	
	        	if(request.getParameter("search_destinations") != null) {
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
						
					PlanController planController = new PlanController();
		        	DestinationBean destinationBean = new DestinationBean();
		        	destinationBean.setSea(radioSea);
		        	destinationBean.setMountain(radioMountain);
		        	destinationBean.setArt(cbArt);
		        	destinationBean.setYoung(cbYoung);
		        	destinationBean.setContinent(continent);
		        	
		        	List<DestinationBean> dests;
		        	dests = planController.findSpecialDestination(destinationBean);
		        	
		        	Random rand = new Random();
		        	int index = rand.nextInt(dests.size());
		        	session.setAttribute("destination", dests.get(index));
		        	response.sendRedirect("plan.jsp");
				}
	        %>
	        
		        
   			</div>
    	
    	<%
			}
    	%>

    </body>
</html>


		        