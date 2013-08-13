<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<html>

<head>
  <title>Call Log</title>

<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />   
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>

<style type="text/css">
  body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 50px;}
  td{ font: 12px sans-serif;}
  .demoHeaders { margin-top: 2em; }
  em {
    font-weight: bold;
    font-style: normal;
    color: #f00;
  }
  
    .myheader { 
     font: 18px sans-serif;
     color: "#00f";
     background-color:#b0c4de;
     }
     
 hr {
  border: 0;
  width: 100%;
  color: #f00;
background-color: #205c90;
height: 5px;
}    
           
</style>  

</head>
<body>

<table>
 

<tr>
<td align="right" valign="middle">


<a href="/calllog"><img border=0 src="resources/images/logo.png" border="0"/></a>
 <c:if test="${not empty currentUser}">


Welcome, ${currentUser} | <a href="<c:url value="j_spring_security_logout" />" > Logout</a>
</c:if>
</td>
</tr>
<tr><td><hr size="5"></td></tr>
<tr><td>


