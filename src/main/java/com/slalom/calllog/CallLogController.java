package com.slalom.calllog;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CallLogController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	CallLogDatabase database;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		String volunteerId = detectUser(model);
		CallLog callLog = new CallLog();
		callLog.setVolunteerId(volunteerId);
			
		model.addAttribute("command", callLog);
		return "create";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, @RequestParam("id") Integer id) {

		detectUser(model);
		CallLog callLog = database.getCallLog(id);
		model.addAttribute("command", callLog);
		return "create";
	}

	public String detectUser(Model model) {

		String volunteerId = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
		  userDetails = (UserDetails) principal;
		}
		
		if(userDetails==null){
			logger.info("no user");
		}else{
		    String userName = userDetails.getUsername();
		    logger.info("USER IS : " + userName);		
		    model.addAttribute("currentUser", userName);
		    
		    Volunteer volunteer = database.getVolunteer(userName);
		    
		    if(volunteer!=null){
		    	 model.addAttribute("volunteer", volunteer);
		    	 volunteerId = volunteer.getVolunteerId();
		    }
		    
		    
		    
		}
		
		return volunteerId;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, @ModelAttribute CallLog callLog,
			BindingResult result) {

		detectUser(model);
		int callLogRecordId = database.saveRecord(callLog);
		return "redirect:/?callLogRecordId=" + callLogRecordId;
	}

}
