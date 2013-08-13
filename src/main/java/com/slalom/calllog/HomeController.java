package com.slalom.calllog;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Autowired
	CallLogDatabase database;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest req, Model model) {
		logger.info("Welcome to the Cancer Lifeline call log application.");

		
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
		  userDetails = (UserDetails) principal;
		}
		
		
		
		if(userDetails == null){
			logger.info("Current user is null");
		}else{
			String userName = userDetails.getUsername();		
			logger.info("Current user: " + userName);
		    model.addAttribute("currentUser", userName);
		    
		    Volunteer volunteer = database.getVolunteer(userName);
		    
		    if(volunteer!=null){
		    	 model.addAttribute("volunteer", volunteer);
		    }
		}
		
		int rows = 0;

		try {
			rows = database.countRows();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int pageNo = 1;
		String page = req.getParameter("page");

		try {
			pageNo = Integer.parseInt(page);
		} catch (Exception e) {}

		List<CallLog> callLogs = database.getCallLogs(pageNo);

		model.addAttribute("recordCount", rows);
		model.addAttribute("callLogs", callLogs);
		model.addAttribute("callLogRecordId",req.getParameter("callLogRecordId"));
		model.addAttribute("page",pageNo);
		if(pageNo*10<rows)
		model.addAttribute("nextPage",new Integer(pageNo+1));
		if(pageNo>1)
		  model.addAttribute("prevPage",new Integer(pageNo-1));
		

		return "home";
	}
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest req, Model model) {
		logger.info("login");
		return "login";
	}

}
