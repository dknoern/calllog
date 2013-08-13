<%@ include file="header.jsp"%>


	<p>There are currently ${recordCount} call records entered.</p>

	<c:if test="${!empty callLogRecordId}">

		<p>Saved record number ${callLogRecordId}</p>
	</c:if>

	<form action="/calllog/create" method="get">
		<input type="submit" value="Enter new call log entry" class="button"/>
	</form>

<p>

<script type="text/javascript">
function altRows(id){
	if(document.getElementsByTagName){  
		
		var table = document.getElementById(id);  
		var rows = table.getElementsByTagName("tr"); 
		 
		for(var i = 0; i < rows.length; i++){          
			if(i % 2 == 0){
				rows[i].className = "evenrowcolor";
			}else{
				rows[i].className = "oddrowcolor";
			}      
		}
	}
}

window.onload=function(){
	altRows('alternatecolor');
}
</script>


<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style type="text/css">
table.altrowstable {
	font-family: verdana,arial,sans-serif;
	font-size:12px;
	color:#333333;
	border-width: 1px;
	border-color: #a9c6c9;
	border-collapse: collapse;
}
table.altrowstable th {
	border-width: 1px;
		font-family: verdana,arial,sans-serif;
	font-size:13px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.altrowstable td {
	border-width: 1px;
	padding: 5px;
	border-style: solid;
	border-color: #a9c6c9;
}
.oddrowcolor{
	background-color:#d4e3e5;
}
.evenrowcolor{
	background-color:#c3dde0;
}
</style>


<!-- Table goes in the document BODY -->
<table class="altrowstable" id="alternatecolor">



<tr>
	<th>ID</th><th>Volunteer</th><th>Date/time</th><th>Caller</th><th>Status</th>
</tr>
      <c:forEach var="callLog" items="${callLogs}">
        <tr>
        <c:choose>
          <c:when test="${volunteer.volunteerId == callLog.volunteerId || volunteer.role == 'ADMIN_ROLE'}">
            <td><a href="edit?id=${callLog.id}">${callLog.id}</a></td>
          </c:when>
          <c:otherwise>
            <td>${callLog.id}</td>
          </c:otherwise>
        </c:choose>
          <td>${callLog.volunteerId}</td>
          <td>${callLog.callDate}</td>
          <td>${callLog.callerIs}</td>
          <td>${callLog.callStatus}</td>
        </tr>
      </c:forEach>


</table>
<p>
Page 

<c:if test="${prevPage != null}"><a href="/calllog?page=${prevPage}">&lt;</a></c:if> ${page} <c:if test="${nextPage != null}"><a href="/calllog?page=${nextPage}">&gt;</a></c:if>

<!--  The table code can be found here: http://www.textfixer/resources/css-tables.php#css-table03 -->


</p>

<%@ include file="footer.jsp"%>
