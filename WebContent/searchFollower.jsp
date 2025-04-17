<%@page import="logic.exceptions.AddFollowerException"%>
<%@page import="logic.exceptions.FollowRequestException"%>
<%@page import="logic.controllers.SearchFollowController"%>
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
        <title>Search follower</title>
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
			  text-align: right;
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
				List<UserBean> usersToFollow = new ArrayList<>();
				SearchFollowController searchFollowController = new SearchFollowController();
				String username = ((UserBean) session.getAttribute("user")).getUsername();
				String usernameToSearch = "";
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
                          <li class="active"><a href="searchFollower.jsp">Search follower</a></li>
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
                <div class="col-sm-3 sidenav" style="float:left; width:450px; border-right: 1px solid #000000; margin:5px">
                
                	<form action="searchFollower.jsp" method="post">
			    	 <div class="form-group">
			    	 
			    	 <%
			    	 	out.println("<h3 style=\"color:black\"> &nbsp;&nbsp;&nbsp;Insert the username to search: </h3> <input class=\"form-control\" id=\"follower\" name=\"follower\" style=\"height:30px; width:300px; margin-left: 20px;\" placeholder=\"Username\" type=\"text\" required; /> <br> <input type=\"submit\" name=\"search_follower\" value=\"Search\" class=\"btn btn-warning btn-sm\" style=\"float:left; margin-left: 20px;\">");			
			    	 %>
			    	 
			    	 <% 
		    
				    	try{
						    if(request.getParameter("search_follower") != null){
								usernameToSearch = request.getParameter("follower");
								usersToFollow = searchFollowController.loadUsers(username, usernameToSearch);
								session.setAttribute("usersToFollow", usersToFollow);
							}
					    
							if(request.getParameter("send_follow_request") != null) {
								
								if(usersToFollow != null){
									for(UserBean user : (List<UserBean>)session.getAttribute("usersToFollow")){
										if(user.getUsername().equalsIgnoreCase(request.getParameter("send_follow_request"))){
											searchFollowController.sendFollowRequest(username, user.getUsername());
											session.setAttribute("request_correctly_sent", "Request correctly sent!");
											session.removeAttribute("send_follow_request");
											session.removeAttribute("usersToFollow");
											break;
										}
									}
								}
							}
							
						} catch (SystemException e){
							session.setAttribute("system_exception", e.getMessage());
						} catch (FollowRequestException e){
							session.setAttribute("follow_request_exception", e.getMessage());
						} catch (AddFollowerException e){
							session.setAttribute("add_follower_exception", e.getMessage());
						}
				    
				    %>
				    
		        	</div>	
		        </form> 
		        
		       </div>   	 	

                <div id="trvls"  style="float: left; width: 500px">
                	
                    <%		
						out.println("<h4>Results:</h4>");
						
						for(UserBean userBean : usersToFollow) { 
							
							String pic = "";
							String path = "";
                               
                               pic = userBean.getPhoto();
                               if(pic.equals(""))
                                  out.println("<div style=\"background-color:#FFEBCD\" class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a><img src=\"Images/userButton.png\" class=\"img-circle\" height=\"70\" width=\"70\" alt=\"Avatar\"><br><p style= \"color: black; display:inline\"><strong> Username: " + " </strong></p> <p style= \"color: black; display:inline\"> " + userBean.getUsername() + "</p><p></p><p style= \"color: black; display:inline\"><strong> Email: " + " </strong></p> <p style= \"color: black; display:inline\"> " + userBean.getEmail() + "</p> <form action=\"searchFollower.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"send_follow_request\" value=\""+ userBean.getUsername() +"\"><input type=\"submit\" value=\"Send request\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form></div>");
							else {
                                  pic = pic.replace("\\", "/");
                                  String[] params;
                                  params = pic.split("(?<=/)");
                                  for(int i=0; i<params.length; i++) {
                                      if(params[i].equalsIgnoreCase("Images/")) {    
                                          for(int j=i; j<params.length; j++)
                                              path = path.concat(params[j]);
                                          break;
                                      }
                                  }
                                  out.println("<div style=\"background-color:#FFEBCD\" class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a><img src=\"" + path + "\" class=\"img-circle\" height=\"70\" width=\"70\" alt=\"Avatar\"><br><p style= \"color: black; display:inline\"><strong> Username: " + " </strong></p> <p style= \"color: black; display:inline\"> " + userBean.getUsername() + "</p><p></p><p style= \"color: black; display:inline\"><strong> Email: " + " </strong></p> <p style= \"color: black; display:inline\"> " + userBean.getEmail() + "</p> <form action=\"searchFollower.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"send_follow_request\" value=\""+ userBean.getUsername() +"\"><input type=\"submit\" value=\"Send request\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form></div>");

                              }
						   
						}
						
			           	if(session.getAttribute("request_correctly_sent") != null) {
							out.println("<h3 style=\"color:green\">" + session.getAttribute("request_correctly_sent") + "</h3>");
							session.removeAttribute("request_correctly_sent");
						}
	           	   
			           	if(session.getAttribute("system_exception") != null) {
							out.println("<h3 style=\"color:red\">" + session.getAttribute("system_exception") + "</h3>");
							session.removeAttribute("system_exception");
						}
			           	
			           	if(session.getAttribute("follow_request_exception") != null) {
							out.println("<h3 style=\"color:red\">" + session.getAttribute("follow_request_exception") + "</h3>");
							session.removeAttribute("follow_request_exception");
						}
			           	
			           	if(session.getAttribute("add_follower_exception") != null) {
							out.println("<h3 style=\"color:red\">" + session.getAttribute("add_follower_exception") + "</h3>");
							session.removeAttribute("add_follower_exception");
						}
						
					%>
                    <hr>
                </div>
            </div>
        </div>
        
        <%
			}
        %>
       
    </body>
</html>


