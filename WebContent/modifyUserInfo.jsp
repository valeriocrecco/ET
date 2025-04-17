<%@page import="java.io.File"%>
<%@page import="logic.exceptions.DuplicateUsernameException"%>
<%@page import="logic.exceptions.UsernameException"%>
<%@page import="logic.exceptions.UsernameSyntaxException"%>
<%@page import="logic.exceptions.PasswordSyntaxException"%>
<%@page import="logic.controllers.ProfileController"%>
<%@page import="logic.bean.PrivateTravelBean"%>
<%@page import="logic.controllers.ManagePrivateTravelController"%>
<%@page import="logic.exceptions.EmailSyntaxException"%>
<%@page import="logic.exceptions.EmailException"%>
<%@page import="logic.exceptions.SystemException"%>
<%@page import="logic.exceptions.PasswordSyntaxException"%>
<%@page import="logic.exceptions.PasswordException"%>
<%@page import="logic.exceptions.DefaultPhotoException"%>
<%@page import="logic.bean.UserBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Profile</title>
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
               background-color: #f0ad4e;
               color: white;
           }
           
           .center {
			  text-align: center;
			}
       
       		.img-usr{
       			height: 55px;
       			width: 55px;
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
				ProfileController profileController = new ProfileController();
				UserBean user = (UserBean) session.getAttribute("user");
				String oldUsername = user.getUsername();
				String oldPassword = user.getPassword();
				oldPassword = oldPassword.replaceAll(".", "&#9679;");
				String oldEmail = user.getEmail();
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
                       <li class="active"><a href="profile.jsp">Profile</a></li>
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
                <div class="col-sm-3 sidenav">
                	<div id="usrInf" class="well center" style="background-color:#f8f8f8">
        	            <%
               				String pic = "";
        	            	String oldPic = ""; 
               				pic = profileController.retrieveUserPhoto(oldUsername);
               				oldPic = pic;
               				if(pic.equals(""))
               					out.println("<img src=\"Images/userButton.png\" class=\"img-circle\" height=\"55\" width=\"55\" alt=\"Avatar\">");
               				else {
               					String path = "";
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
               					out.println("<img src=\"" + path + "\" class=\"img-circle\" height=\"140\" width=\"140\" alt=\"Avatar\">");
               				}
               				out.println("<br>");
               				out.println("<h4>" + oldUsername + " </h4>");
               				out.println("<h4>" + oldEmail + " </h4>");
               				out.println("<h4>" + oldPassword + " </h4>");
               			%>
                    </div>
                    
                    <ul class="nav nav-pills nav-stacked li2">
                        <li><a class="dropdown-toggle" data-toggle="dropdown" href="#">
	    					<span class="caret"></span> Upcoming Travels</a>
						    <ul class="dropdown-menu">
						      <li class="active"><a href="profile.jsp">Private</a></li>
						      <li><a href="profileUpPub.jsp">Public</a></li>
						    </ul>
                        
                        </li>
						<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">
	    					<span class="caret"></span> Past Travels</a>
						<ul class="dropdown-menu">
						      <li class="active"><a href="profilePastPriv.jsp">Private</a></li>
						      <li><a href="profilePastPub.jsp">Public</a></li>
						</ul>
						</li>
                        <li><a href="followers.jsp">Following</a></li>
                        <li class="active dropdown"><a href="#" class="active">Modify personal information</a></li>
                    </ul><br>
                    
                </div>
                
                <div id="trvls" class="col-sm-9">
                    <h4><small>MODIFY YOUR PERSONAL INFORMATION</small></h4>
                    <hr>
                    <% 
			    		 if(request.getParameter("newUsername") != null) {
			    			// Controller applicativo
			    			try {
			    				user.setAndValidateUsername(request.getParameter("newUsername"));
				    			profileController.changeUsername(oldUsername, request.getParameter("newUsername"));
				    			session.setAttribute("user", user);
				    			response.sendRedirect("modifyUserInfo.jsp");
			    			} catch (UsernameSyntaxException|SystemException|UsernameException|DuplicateUsernameException e) {
			    				user.setUsername(oldUsername);
			    				session.setAttribute("username_error", e.getMessage());
			    			}    			
			    		 }
                    
	                     if(request.getParameter("newPassword") != null) {
			    			// Controller applicativo
			    			try {
				    			user.setAndValidatePassword(request.getParameter("newPassword"));
				    			profileController.changePassword(oldUsername, request.getParameter("newPassword"));
								session.setAttribute("user", user);
								response.sendRedirect("modifyUserInfo.jsp");
			    			} catch (PasswordSyntaxException|PasswordException|SystemException e) {
			    				user.setPassword(oldPassword);
			    				session.setAttribute("password_error", e.getMessage());
			    			}   			
			    		 }
	                     
	                     if(request.getParameter("newEmail") != null) {
			    			// Controller applicativo
			    			try {
				    			user.setAndValidateEmail(request.getParameter("newEmail"));
				    			profileController.changeEmail(oldUsername, request.getParameter("newEmail"));
				    			session.setAttribute("user", user);
				    			response.sendRedirect("modifyUserInfo.jsp");
			    			} catch (EmailSyntaxException|EmailException|SystemException e) {
				   			    user.setEmail(oldEmail);
								session.setAttribute("email_error", e.getMessage());		
			    			}
			    		 }
					%>
                    <form action="modifyUserInfo.jsp" method="post">
                    	<label>New username</label>
				        <div class="input-group">
	                        <span class="input-group-addon"><em class="glyphicon glyphicon-user"></em></span>
	                        <input name="newUsername" style="max-width:500px" placeholder="New username" class="form-control" type="text" required>
                   			<input type="submit" class="btn btn-warning" style="float:left; margin-left:5px;" value="Modify">
                   		</div>
                   		<br>
                   	</form>
                    <form action="modifyUserInfo.jsp" method="post">
                   		<label>New email address</label>
						<div class="input-group">
	                        <span class="input-group-addon"><em class="glyphicon glyphicon-envelope"></em></span>
	                        <input name="newEmail" style="max-width:500px" placeholder="New email address" class="form-control" type="email" required>
	                    	<input type="submit" class="btn btn-warning" style="float:left; margin-left:5px;" value="Modify">
	                    </div>
	                    <br>
	                </form>
	                <form action="modifyUserInfo.jsp" method="post">
	                    <label>New password</label>
						<div class="input-group">
	                        <span class="input-group-addon"><em class="glyphicon glyphicon-lock"></em></span>
	                        <input name="newPassword" style="max-width:500px" placeholder="New password" class="form-control" type="password" required>
	                    	<input type="submit" class="btn btn-warning" style="float:left; margin-left:5px;" value="Modify">
	                    </div>
	                    <br>
					</form>
					<form action="" method="post" enctype="multipart/form-data">
						<label>Change photo</label>
						<div class="input-group">
							<span class="input-group-addon"><em class="glyphicon glyphicon-picture"></em></span>
	                        <input type="file" name="file" class="form-control" style="max-width:500px">
	                        <input type="submit" class="btn btn-warning" style="float:left; margin-left:5px;" value="Modify">
						</div>
					</form>
					<% 
						if(session.getAttribute("username_error") != null) {
							out.println("<p style=\"color:red\">" + session.getAttribute("username_error") + "</p>");
							session.removeAttribute("username_error");
						}
			            if(session.getAttribute("password_error") != null) {
							out.println("<p style=\"color:red\">" + session.getAttribute("password_error") + "</p>");
							session.removeAttribute("password_error");
						}	
			            if(session.getAttribute("email_error") != null) {
							out.println("<p style=\"color:red\">" + session.getAttribute("email_error") + "</p>");
							session.removeAttribute("email_error");
						}
			            if(session.getAttribute("photo_error") != null) {
							out.println("<p style=\"color:red\">" + session.getAttribute("photo_error") + "</p>");
							session.removeAttribute("photo_error");
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