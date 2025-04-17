<%@page import="logic.exceptions.SeatsNotAvailableException"%>
<%@page import="logic.exceptions.SystemException"%>
<%@page import="logic.bean.ReplyNotificationBean"%>
<%@page import="logic.bean.FollowNotificationBean"%>
<%@page import="logic.bean.JoinNotificationBean"%>
<%@page import="logic.bean.UserBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="logic.controllers.NotifyController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Notifications</title>
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
			  width: 33.33%;
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
				NotifyController notifyController = new NotifyController();

				String username = ((UserBean) session.getAttribute("user")).getUsername();
				
				List<JoinNotificationBean> joinNotificationBeans = new ArrayList<>();
				List<FollowNotificationBean> followNotificationBeans = new ArrayList<>();
				List<ReplyNotificationBean> replyNotificationBeans = new ArrayList<>();
				joinNotificationBeans = notifyController.retrieveJoinNotifications(username);
				followNotificationBeans = notifyController.retrieveFollowNotifications(username);
				replyNotificationBeans = notifyController.retrieveReplyNotifications(username);
				
				try {
					if(request.getParameter("join_notification_accept") != null) {
						for(JoinNotificationBean joinNotificationBean: joinNotificationBeans){
							if(joinNotificationBean.getIdJoin() == Integer.parseInt(request.getParameter("join_notification_accept"))){
								notifyController.acceptJoinNotification(username, joinNotificationBean);
								break;
							}
						}
					}
					
					if(request.getParameter("join_notification_decline") != null) {
						for(JoinNotificationBean joinNotificationBean: joinNotificationBeans){
							if(joinNotificationBean.getIdJoin() == Integer.parseInt(request.getParameter("join_notification_decline"))){
								notifyController.declineJoinNotification(username, joinNotificationBean);
								break;
							}
						}
					}
					
					if(request.getParameter("follow_notification_accept") != null) {
						for(FollowNotificationBean followNotificationBean: followNotificationBeans){
							if(followNotificationBean.getIdFollow() == Integer.parseInt(request.getParameter("follow_notification_accept"))){
								notifyController.acceptFollowNotification(username, followNotificationBean);
								break;
							}
						}
					}
			
					if(request.getParameter("follow_notification_decline") != null) {
						for(FollowNotificationBean followNotificationBean: followNotificationBeans){
							if(followNotificationBean.getIdFollow() == Integer.parseInt(request.getParameter("follow_notification_decline"))){
								notifyController.declineFollowNotification(username, followNotificationBean);
								break;
							}
						}
					}
					
					if(request.getParameter("reply_notification") != null) {
						for(ReplyNotificationBean replyNotificationBean: replyNotificationBeans){
							if(replyNotificationBean.getIdReply() == Integer.parseInt(request.getParameter("reply_notification"))){
								notifyController.deleteNotification(replyNotificationBean);
								break;
							}
						}
					}
					
	    		} catch (SystemException e) {
	    			session.setAttribute("system_error", e.getMessage());
	    		} catch (SeatsNotAvailableException e) {
	    			session.setAttribute("seats_available_error", e.getMessage());
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
                        <li class="active"><a href="notifs.jsp">Notifications</a></li>
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
		           	if(session.getAttribute("system_error") != null) {
						out.println("<h3 style=\"color:red\">" + session.getAttribute("system_error") + "</h3>");
						session.removeAttribute("system_error");
					}
		           	if(session.getAttribute("seats_available_error") != null) {
						out.println("<h3 style=\"color:red\">" + session.getAttribute("seats_available_error") + "</h3>");
						session.removeAttribute("seats_available_error");
					}
           	   %>
               <div class="row">
                   <div id="joinnotifs" class="column">
						<%		
							out.println("<h4>Join notifications: " + joinNotificationBeans.size() + "</h4>");
							
							for(JoinNotificationBean joinNotificationBean : joinNotificationBeans) { 
								out.println("<div style=\"background-color:#FFEBCD\" class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a><p><strong>Hey!</strong></p>" + joinNotificationBean.getMsgJoin() + "<form action=\"notifs.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"join_notification_accept\" value=\""+ joinNotificationBean.getIdJoin() +"\"><input type=\"submit\" value=\"Accept\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form><form action=\"notifs.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"join_notification_decline\" value=\""+ joinNotificationBean.getIdJoin() +"\"><input type=\"submit\" value=\"Decline\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form></div>");
							}
						%>
                       <hr>
                   </div>
                   <div id="follnotifs" class="column">
		       			<%		
		       				out.println("<h4>Follow notifications: " + followNotificationBeans.size() + "</h4>");	
			       			
		       				for(FollowNotificationBean followNotificationBean : followNotificationBeans) {
								out.println("<div style=\"background-color:#FFEBCD\" class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a><p><strong>Hey!</strong></p>" + followNotificationBean.getMsgFollow() + "<form action=\"notifs.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"follow_notification_accept\" value=\""+ followNotificationBean.getIdFollow() +"\"><input type=\"submit\" value=\"Follow\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form><form action=\"notifs.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"follow_notification_decline\" value=\""+ followNotificationBean.getIdFollow() +"\"><input type=\"submit\" value=\"Decline\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form></div>");
							}
						%>
                       <hr>
                   </div>
                   <div id="repnotifs" class="column">
					   <%	
					   		out.println("<h4>Reply notifications: " + replyNotificationBeans.size() + "</h4>");
						  	
					   		for(ReplyNotificationBean replyNotificationBean : replyNotificationBeans) {
								out.println("<div style=\"background-color:#FFEBCD\" class=\"alert alert-success fade in\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">x</a><p><strong>Hey!</strong></p>" + replyNotificationBean.getMsgReply() + "<form action=\"notifs.jsp\" method=\"post\"><input type=\"HIDDEN\" name=\"reply_notification\" value=\""+ replyNotificationBean.getIdReply() +"\"><input type=\"submit\" value=\"Delete\" class=\"btn btn-warning btn-sm\" style=\"float:right\"></form></div>");
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


