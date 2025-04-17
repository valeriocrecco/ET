<%@page import="logic.exceptions.SystemException"%>
<%@page import="logic.exceptions.LoginException"%>
<%@page import="logic.exceptions.PasswordSyntaxException"%>
<%@page import="logic.exceptions.UsernameSyntaxException"%>
<%@page import="logic.bean.UserBean"%>
<%@page import="logic.controllers.LoginController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>EasyTavel - Login</title>
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
       
           .login-panel {
               max-width: 320px;
               min-height:300px;
               float: right;
               margin-right: 30px;
               margin-top: 10px;
               border-top: 1px solid rgba(0, 0, 0, 0.1);
               border-bottom: 1px solid rgba(0, 0, 0, 0.2);
               border-right: 1px solid rgba(0, 0, 0, 0.1);
               border-left: 1px solid rgba(0, 0, 0, 0.1);

           }
       
           .welcome-text{
               float: left;
               max-width: 520px;
               min-height:300px;
               margin-left:100px;
               margin-top:0px;
               margin-bottom:0px;
           }
       	
       		h4 {
       			font-style: italic;
       		}
       		
           .affix {
               top: 0;
               width: 100%;
               z-index: 9999 !important;
           }
       
           .affix + .container-fluid {
               padding-top: 70px;
           }
       
       	   .error {
       	   		color: red;
       	   }
       </style>
         
    </head>
    <body>   	
		<%
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0
			response.setHeader("Expires", "0"); // Proxies			
		%>
        <div>
            <div class="container-fluid" style="background-color:#222">
                <img class="img-logo" src="Images/logoET.png" alt="LogoET">
            </div>
        </div>

        <nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="197">
            <div class="container-fluid">
                
	            <div class="collapse navbar-collapse" id="myNavbar">
	                <ul class="nav navbar-nav navbar-right">
	                    <li><a href="signup.jsp"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
	                    <li class="active"><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
	                </ul>
	            </div>
            </div>
        </nav>
    
       <div class="container-fluid">
			<div class="welcome-text">
				<br>
				<br>
			    <h1>Welcome in Easy Travel</h1>
			    <br>
			    <h4>Joining our community you can plan your own travels and share them with your friends.</h4>
			</div>
           
			<div class="panel login-panel">
			    <div class="panel-body">
			    	<% 
			    		if(request.getParameter("username") != null && request.getParameter("password") != null) {
			    			// Controller applicativo
			    			UserBean userBean = new UserBean();
			    			try {
			    				userBean.setAndValidateUsername(request.getParameter("username"));
				    			userBean.setAndValidatePassword(request.getParameter("password"));
				    			LoginController loginController = new LoginController();
				    			loginController.login(userBean);
				    			
				    			// Redirect alla Home page
				    			session.setAttribute("user", userBean);
				    			response.sendRedirect("home.jsp");
				    			
			    			} catch(PasswordSyntaxException e) {
			    				session.setAttribute("password_error", e.getMessage());
			    		    } catch(UsernameSyntaxException e) {
			    				session.setAttribute("username_error", e.getMessage());
			    			} catch(LoginException|SystemException e) {
			    				session.setAttribute("login_error", e.getMessage());
			    			} 	    			
			    		}
					%>
			        <form action="login.jsp" method="post">
				        <div class="form-group">
				            <label for="usr">Username</label>
				            <input type="text" class="form-control" id="usr" style="min-width:280px" name="username" placeholder="Username" required>
						</div>
						<div class="form-group">
							<label for="pwd">Password</label>
							<input type="password" class="form-control" id="pwd" style="min-width:280px" name="password" placeholder="Password">
						</div>
						<% 
							if(session.getAttribute("username_error") != null) {
								out.println("<p style=\"color:red\">" + session.getAttribute("username_error") + "</p>");
								session.removeAttribute("username_error");
							}
				            if(session.getAttribute("password_error") != null) {
								out.println("<p style=\"color:red\">" + session.getAttribute("password_error") + "</p>");
								session.removeAttribute("password_error");
							}	
				            if(session.getAttribute("login_error") != null) {
								out.println("<p style=\"color:red\">" + session.getAttribute("login_error") + "</p>");
								session.removeAttribute("login_error");
							}
						%>
						<br>
						<br>
						<br>
						<br>
						<div>
							<input type="submit" class="btn btn-warning" style="float:right" value="Login">
						</div>
					</form>
				</div>
			</div>
        </div>
    </body>
</html>