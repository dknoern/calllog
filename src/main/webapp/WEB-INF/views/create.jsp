<%@ include file="header.jsp"%>



<script type="text/javascript">
  
$(function(){
  $('#date').datepicker();
  $('#callerZip').style.backgroundColor='#0f0';
});



function toggleOtherField(fieldName,otherFieldName){
   var x=document.forms["calllog"][fieldName].value;
   if(x=="Other"){
	     document.forms["calllog"][otherFieldName].disabled=false;
   }else{
     document.forms["calllog"][otherFieldName].disabled=true;
     document.forms["calllog"][otherFieldName].value="";
   }
}

function toggleOtherFieldForCheckbox(obj,otherFieldName){
	  
	   if(obj.checked){
	     document.forms["calllog"][otherFieldName].disabled=false;
	   }else{
	     document.forms["calllog"][otherFieldName].disabled=true;
	     document.forms["calllog"][otherFieldName].value="";
	   }
}

function literatureOnClick(){

   var cboxes=document.forms["calllog"]["literature"];
   var disableContactAddress = true;
   for(var i=0;i<cboxes.length;i++){
       if(cboxes[i].checked){
         disableContactAddress =false;
       }
   }
   document.forms["calllog"]["contactAddress1"].disabled=disableContactAddress;
   document.forms["calllog"]["contactAddress2"].disabled=disableContactAddress;
   document.forms["calllog"]["contactCity"].disabled=disableContactAddress;
   document.forms["calllog"]["contactState"].disabled=disableContactAddress;
   document.forms["calllog"]["contactZip"].disabled=disableContactAddress;
   document.forms["calllog"]["contactEmail"].disabled=disableContactAddress;

}

function validateForm(){

	var formOk=true;
	var volunteerId = document.forms["calllog"]["volunteerId"].value;
	if(volunteerId==null || volunteerId==""){
		document.forms["calllog"]["volunteerId"].style.backgroundColor="#faa";
		formOk = false;
	}
	
	var lengthOfCall = document.forms["calllog"]["lengthOfCall"].value;
	if(lengthOfCall==null||lengthOfCall==""||!isInteger(lengthOfCall)){
		document.forms["calllog"]["lengthOfCall"].style.backgroundColor="#faa";
		formOk = false;
	}	
	
	
	if(document.forms["calllog"]["callerIs"].value=="Other" && document.forms["calllog"]["callerIsOther"].value==""){
		formOk = false;
		document.forms["calllog"]["callerIsOther"].style.backgroundColor="#faa";
	}
	
	if(document.forms["calllog"]["callStatus"].value=="Other" && document.forms["calllog"]["callStatusOther"].value==""){
		formOk = false;
		document.forms["calllog"]["callStatusOther"].style.backgroundColor="#faa";
	}	
	
	if(document.forms["calllog"]["referral"].value=="Other" && document.forms["calllog"]["referralOther"].value==""){
		formOk = false;
		document.forms["calllog"]["referralOther"].style.backgroundColor="#faa";
	}	

	if(document.forms["calllog"]["leadSource"].value=="Other" && document.forms["calllog"]["leadSourceOther"].value==""){
		formOk = false;
		document.forms["calllog"]["leadSourceOther"].style.backgroundColor="#faa";
	}		
	
	   var cboxes=document.forms["calllog"]["literature"];
	   for(var i=0;i<cboxes.length;i++){
	       if(cboxes[i].checked && cboxes[i].value=="Other" && document.forms["calllog"]["literatureOther"].value==""){
	          formOk = false;
	   		  document.forms["calllog"]["literatureOther"].style.backgroundColor="#faa";
	       }
	   }	
	
	   var cboxes=document.forms["calllog"]["callTopic"];
	   for(var i=0;i<cboxes.length;i++){
	       if(cboxes[i].checked && cboxes[i].value=="Other" && document.forms["calllog"]["callTopicOther"].value==""){
	          formOk = false;
	   		  document.forms["calllog"]["callTopicOther"].style.backgroundColor="#faa";
	       }
	   }	
	
	
	
	
	if(!formOk){
	  alert("Please enter required values.");
	  return false;
	}else{
		return true;
	}
}


function isEmpty(s)
{   return ((s == null) || (s.length == 0))
}

var reInteger = /^\d+$/

function isInteger (s)
{   var i;

    if (isEmpty(s)) 
       if (isInteger.arguments.length == 1) return defaultEmptyOK;
       else return (isInteger.arguments[1] == true);

    return reInteger.test(s)
}


</script>

<form:form name="calllog" onsubmit="return validateForm()">



<table>

<tr>
  <td colspan="4">&nbsp;</td>
</tr>
<tr>
  <td align="right">Volunteer ID <em>*</em></td>
  <td><form:input path="volunteerId" size="10" maxlength="10"/></td>
  <td>Length of call (min) <em>*</em></td>
  <td><form:input path="lengthOfCall" size="4" maxlength="4"/></td>
</tr>
<tr>
  <td align="right">Call date</td>
  <td><form:input path="callDate" id="date" size="10" maxlength="10"/></td>
  <td align="right">Time</td>
  <td><form:input path="callHour" size="2" maxLength="2"/>:<form:input path="callMinute" size="2" maxLength="2"/>
    <form:select path="callAmPm">
      <form:option value="AM">AM</form:option>
      <form:option value="PM">PM</form:option>
    </form:select>
  </td>
</tr>
<tr>
  <td colspan="4"><b>Caller Info</b></td>
</tr>
<tr>
  <td align="right">First name</td>
  <td><form:input path="firstName" size="15" maxlength="40"/></td>
  <td align="right">Caller Zip</td>
  <td><form:input path="callerZip" size="10" maxlength="10"/></td>
</tr>

<tr>
  <td align="right">Last name</td>
  <td><form:input path="lastName" size="15" maxlength="40"/></td>
  <td align="right">Phone number</td>
  <td><form:input path="callerPhoneNumber" size="15" maxlength="40"/></td>
</tr>
</table>
<table>
<tr>
  <td align="right">Caller is</td>
  <td>
    <form:select path="callerIs" onChange="toggleOtherField('callerIs','callerIsOther')">
      <form:option value="">(Select One)</form:option>
      <form:option value="Patient">Patient</form:option>
      <form:option value="Sibling">Sibling</form:option>
      <form:option value="Parent">Parent</form:option>
      <form:option value="Unknown">Unknown</form:option>
      <form:option value="Other">Other</form:option>
    </form:select><form:input path="callerIsOther" disabled="${empty command.callerIsOther}"/></td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
</tr>
<tr>
  <td align="right">Call status</td>
  <td>
    <form:select path="callStatus" onChange="toggleOtherField('callStatus','callStatusOther')">
      <form:option value="">(Select One)</form:option>
      <form:option value="New">New</form:option>
      <form:option value="Repeat">Repeat</form:option>
      <form:option value="On-going">On-going</form:option>
      <form:option value="Unknown">Unknown</form:option>
      <form:option value="Other">Other</form:option>
    </form:select><form:input path="callStatusOther" disabled="${empty command.callStatusOther}"/></td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
</tr>

</table>

<table>
<tr>
  <td colspan="6"><hr/><b>Topics Covered</b> (check all that apply)</td>
</tr>
<tr>
  <td><form:checkbox path="callTopic" value="Call registration (website)"/></td>
  <td>Call registration (website)</td>
  <td><form:checkbox path="callTopic" value="Professional"/></td>
  <td>Professional</td>
  <td><form:checkbox path="callTopic" value="Resource Referral"/></td>
  <td>Resource Referral</td>
</tr>
<tr>
  <td><form:checkbox path="callTopic" value="Family Issues"/></td>
  <td>Family Issues</td>
  <td><form:checkbox path="callTopic" value="Financial Navigation"/></td>
  <td>Financial Navigation</td>
  <td><form:checkbox path="callTopic" value="Information"/></td>
  <td>Information</td>
</tr>
<tr>
  <td><form:checkbox path="callTopic" value="Vent/Talk"/></td>
  <td>Vent/Talk</td>
  <td><form:checkbox path="callTopic" value="Other" onClick="toggleOtherFieldForCheckbox(this,'callTopicOther')"/></td>
  <td colspan="3">Other 
  <form:input path="callTopicOther" disabled="${empty command.callTopicOther}"/>
  </td>
  
</tr>

</table>


<table>
<tr>
  <td colspan="2"><b>Call description</b> (Don't use Enter key to separate lines. Just type continuously for easier 
data export.)</td>
</tr>
<tr>
  <td colspan="2"><form:textarea path="description" rows="8" cols="60"/></td>
</tr>



<tr>
  <td colspan="2"><b>Refer caller to</b></td>
</tr>
<tr>
  <td colspan="2"><form:select path="referral" onChange="toggleOtherField('referral','referralOther')">
      <form:option value="">(Select One)</form:option>
      <form:option value="211">211</form:option>
      <form:option value="American Cancer Society">American Cancer Society</form:option>
      <form:option value="Cancer Information Services">Cancer Information Services</form:option>
      <form:option value="Cancer Lifeline Financial Navigation">Cancer Lifeline Financial Navigation</form:option>
      <form:option value="Patient's Physician/Hospital Worker">Patient's Physician/Hospital Worker</form:option>
      <form:option value="Senior Information and Assistance">Senior Information and Assistance</form:option>
      <form:option value="Other">Other</form:option>
    </form:select><form:input path="referralOther" disabled="${empty command.referralOther}"/></td>
</tr>

<tr>
  <td colspan="2"><b>Quotes and anecdotal information</b></td>
</tr>
<tr>
  <td colspan="2"><form:textarea path="quotes" rows="3" cols="60"/></td>
</tr>


<tr>
  <td colspan="2"><b>How did the caller learn about CLL/this class?</b></td>
</tr>
<tr>
  <td colspan="2"><form:select path="leadSource" onChange="toggleOtherField('leadSource','leadSourceOther')">
      <form:option value="">(Select best match)</form:option>
      <form:option value="Hospital">Hospital</form:option>
      <form:option value="Friend or Family">Friend or Family</form:option>
      <form:option value="Cancer Lifeline Printed Media (Flyer, Bookmark, Catalog)">Cancer Lifeline Printed Media (Flyer, Bookmark, Catalog)</form:option>
      <form:option value="Cancer Lifeline Website">Cancer Lifeline Website</form:option>
      <form:option value="Google">Google</form:option>
      <form:option value="Facebook">Facebook</form:option>
      <form:option value="Twitter">Twitter</form:option>
      <form:option value="Paper or Online Phone Book">Paper or Online Phone Book</form:option>
      <form:option value="Other">Other</form:option>
    </form:select><form:input path="leadSourceOther" disabled="${empty command.leadSourceOther}"/></td>
</tr>

</table>

<table>
<tr>
  <td colspan=5><b>Send the following literature</b></td>
</tr>


<tr>
  <td><form:checkbox path="literature" value="Mail Catalog" onClick="literatureOnClick()"/></td>
  <td>Mail Catalog</td>
  <td><form:checkbox path="literature" value="Email Catalog" onClick="literatureOnClick()"/></td>
  <td>Email Catalog</td>
  <td><form:checkbox path="literature" value="E-newsletter" onClick="literatureOnClick()"/></td>
  <td>E-newsletter</td>
</tr>
<tr>
  <td><form:checkbox path="literature" value="Other CL Information" onClick="literatureOnClick()"/></td>
  <td>Other CL Information</td>
  <td><form:checkbox path="literature" value="Other" onClick="literatureOnClick();toggleOtherFieldForCheckbox(this,'literatureOther')"/></td>
  <td colspan="3">Other <form:input path="literatureOther" disabled="${empty command.literatureOther}"/>
 
  </td>
</tr>
</table>

<table>
<tr>
  <td colspan=2><b>Caller Address</b>
  
    <c:if test="${not empty command.literature}">
  literature not empty
  </c:if>
    <c:if test="${empty command.literature}">
  literature is empty
  </c:if>  
  </td>
</tr>
<tr>
  <td align="right">Address 1</td>
  <td><form:input path="contactAddress1" size="15" maxlength="40" disabled="${empty command.literature}"/></td>
</tr>
<tr>
  <td align="right">Address 2</td>
  <td><form:input path="contactAddress2" size="15" maxlength="40" disabled="${empty command.literature}"/></td>
</tr>
<tr>
  <td align="right">City</td>
  <td><form:input path="contactCity" size="15" maxlength="40" disabled="${empty command.literature}"/></td>
</tr>
<tr>
  <td align="right">State</td>
  <td><form:select path="contactState" disabled="${empty command.literature}"> 
<form:option value="" selected="selected">(Select One)</form:option> 
<form:option value="AL">Alabama</form:option> 
<form:option value="AK">Alaska</form:option> 
<form:option value="AZ">Arizona</form:option> 
<form:option value="AR">Arkansas</form:option> 
<form:option value="CA">California</form:option> 
<form:option value="CO">Colorado</form:option> 
<form:option value="CT">Connecticut</form:option> 
<form:option value="DE">Delaware</form:option> 
<form:option value="DC">District Of Columbia</form:option> 
<form:option value="FL">Florida</form:option> 
<form:option value="GA">Georgia</form:option> 
<form:option value="HI">Hawaii</form:option> 
<form:option value="ID">Idaho</form:option> 
<form:option value="IL">Illinois</form:option> 
<form:option value="IN">Indiana</form:option> 
<form:option value="IA">Iowa</form:option> 
<form:option value="KS">Kansas</form:option> 
<form:option value="KY">Kentucky</form:option> 
<form:option value="LA">Louisiana</form:option> 
<form:option value="ME">Maine</form:option> 
<form:option value="MD">Maryland</form:option> 
<form:option value="MA">Massachusetts</form:option> 
<form:option value="MI">Michigan</form:option> 
<form:option value="MN">Minnesota</form:option> 
<form:option value="MS">Mississippi</form:option> 
<form:option value="MO">Missouri</form:option> 
<form:option value="MT">Montana</form:option> 
<form:option value="NE">Nebraska</form:option> 
<form:option value="NV">Nevada</form:option> 
<form:option value="NH">New Hampshire</form:option> 
<form:option value="NJ">New Jersey</form:option> 
<form:option value="NM">New Mexico</form:option> 
<form:option value="NY">New York</form:option> 
<form:option value="NC">North Carolina</form:option> 
<form:option value="ND">North Dakota</form:option> 
<form:option value="OH">Ohio</form:option> 
<form:option value="OK">Oklahoma</form:option> 
<form:option value="OR">Oregon</form:option> 
<form:option value="PA">Pennsylvania</form:option> 
<form:option value="RI">Rhode Island</form:option> 
<form:option value="SC">South Carolina</form:option> 
<form:option value="SD">South Dakota</form:option> 
<form:option value="TN">Tennessee</form:option> 
<form:option value="TX">Texas</form:option> 
<form:option value="UT">Utah</form:option> 
<form:option value="VT">Vermont</form:option> 
<form:option value="VA">Virginia</form:option> 
<form:option value="WA">Washington</form:option> 
<form:option value="WV">West Virginia</form:option> 
<form:option value="WI">Wisconsin</form:option> 
<form:option value="WY">Wyoming</form:option>
</form:select>

</td>
</tr>
<tr>
  <td align="right">ZIP</td>
  <td><form:input path="contactZip" size="15" maxlength="40" disabled="${empty command.literature}"/></td>
</tr>
<tr>
  <td align="right">Email</td>
  <td><form:input path="contactEmail" size="25" maxlength="40" disabled="${empty command.literature}"/></td>
</tr>
</table>
<table>
<tr>
<td>
<input type="submit" name="Save" value="Save"/>


</td>
</tr>
</table>

</form:form>





<%@ include file="footer.jsp"%>
