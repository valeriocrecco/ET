<%@page import="logic.exceptions.SystemException"%>
<%@page import="logic.exceptions.PasswordSyntaxException"%>
<%@page import="logic.exceptions.EmailSyntaxException"%>
<%@page import="logic.exceptions.UsernameSyntaxException"%>
<%@page import="logic.exceptions.SurnameSyntaxException"%>
<%@page import="logic.exceptions.FirstnameSyntaxException"%>
<%@page import="logic.exceptions.DuplicateUsernameException"%>
<%@page import="logic.controllers.RegistrationController"%>
<%@page import="logic.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
	<head>
	    <title>Signup</title>
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
			
			.signup-panel {
			    width: auto;
			    height:auto;
			    margin-left: 30px;
			    margin-right: 30px;
			    border-top: 1px solid rgba(0, 0, 0, 0.1);
			    border-bottom: 1px solid rgba(0, 0, 0, 0.2);
			    border-right: 1px solid rgba(0, 0, 0, 0.1);
			    border-left: 1px solid rgba(0, 0, 0, 0.1);
			}
			
			.wrapper {
			    text-align: center;
			}
			
			.button-signup {
			    position: bottom;
			    bottom: 50%;
			    margin-top: 10px;
			    margin-bottom: 15px;
			    
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
		                <li class="active"><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
		                <li><a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
		            </ul>
		        </div>
	        </div>
	    </nav>
	    <%
	    	if(request.getParameter("username") != null && request.getParameter("password") != null && request.getParameter("firstName") != null && request.getParameter("lastName") != null &&request.getParameter("email") != null) {
	    		try {
		    		UserBean userBean = new UserBean();
			    	userBean.setAndValidateUsername(request.getParameter("username"));
			    	userBean.setAndValidateEmail(request.getParameter("email"));
			    	userBean.setAndValidateFirstName(request.getParameter("firstName"));
			    	userBean.setAndValidateSecondName(request.getParameter("lastName"));
			    	userBean.setAndValidatePassword(request.getParameter("password"));
			    	
					RegistrationController registrationController = new RegistrationController();
					registrationController.signUp(userBean);
		    		
					session.setAttribute("user", userBean);
	   				
	    			response.sendRedirect("home.jsp");
	    			
				} catch (DuplicateUsernameException e) {
					session.setAttribute("duplicate_username_error", e.getMessage());
				} catch (FirstnameSyntaxException e) {
	   				session.setAttribute("firstname_error", e.getMessage());
				} catch (SurnameSyntaxException e) {
	   				session.setAttribute("lastname_error", e.getMessage());
				} catch (UsernameSyntaxException e) {
	   				session.setAttribute("username_error", e.getMessage());
				} catch (EmailSyntaxException e) {
	   				session.setAttribute("email_error", e.getMessage());
				} catch (PasswordSyntaxException e) {
	   				session.setAttribute("password_error", e.getMessage());
				} catch (SystemException e) {
	   				session.setAttribute("system_error", e.getMessage());
				} 
	    	}
		%>
	    <form class="panel panel-warning signup-panel container-fluid form-horizontal" action="signup.jsp" method="post"  id="contact_form">  
            <div class="panel-heading">Signup</div>
            <br>
            <div class="form-group">
                <label class="col-md-4 control-label">First Name</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><em class="glyphicon glyphicon-user"></em></span>
                        <input name="firstName" placeholder="First Name" class="form-control" type="text" required>
                    </div>
                </div>
            </div>
            <br>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" >Last Name</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><em class="glyphicon glyphicon-user"></em></span>
                        <input name="lastName" placeholder="Last Name" class="form-control" type="text" required>
                    </div>
                </div>
            </div>      
            <br>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label">Email</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><em class="glyphicon glyphicon-envelope"></em></span>
                        <input name="email" placeholder="Email address" class="form-control" type="email" required>
                   </div>
                </div>
            </div>
            <br>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label">Username</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><em class="glyphicon glyphicon-user"></em></span>
                        <input  name="username" placeholder="Username" class="form-control" type="text" required>
                    </div>
                </div>
            </div>
            <br>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" >Password</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <span class="input-group-addon"><em class="glyphicon glyphicon-lock"></em></span>
                        <input name="password" placeholder="Password" class="form-control" type="password" required>
                    </div>
                </div>
            </div>
            <br>
            <%	
				if(session.getAttribute("duplicate_username_error") != null) {
					out.println("<p style=\"color:red\">" + session.getAttribute("duplicate_username_error") + "</p>");
					session.removeAttribute("duplicate_username_error");
				}
	            if(session.getAttribute("username_error") != null) {
					out.println("<p style=\"color:red\">" + session.getAttribute("username_error") + "</p>");
					session.removeAttribute("username_error");
				}	
	            if(session.getAttribute("firstname_error") != null) {
					out.println("<p style=\"color:red\">" + session.getAttribute("firstname_error") + "</p>");
					session.removeAttribute("firstname_error");
				}
	            if(session.getAttribute("lastname_error") != null) {
					out.println("<p style=\"color:red\">" + session.getAttribute("lastname_error") + "</p>");
					session.removeAttribute("lastname_error");
				}
	            if(session.getAttribute("email_error") != null) {
					out.println("<p style=\"color:red\">" + session.getAttribute("email_error") + "</p>");
					session.removeAttribute("email_error");
				}
	            if(session.getAttribute("password_error") != null) {
					out.println("<p style=\"color:red\">" + session.getAttribute("password_error") + "</p>");
					session.removeAttribute("password_error");
				}
	            if(session.getAttribute("system_error") != null) {
					out.println("<p style=\"color:red\">" + session.getAttribute("system_error") + "</p>");
					session.removeAttribute("system_error");
				}
			%>
            <br>
            <div class="wrapper">
                <input type="submit" class="btn btn-warning" value="Sign Up">
            </div>
            <br>
        </form>
	</body>
</html>