package com.herohuapp.parkhyeoncheolboot2.util;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CustomErrorController implements ErrorController {
	@RequestMapping(value="/error",method= {RequestMethod.GET})
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
		if(status != null) {
			String referer = request.getHeader("Referer");
			model.addAttribute("status", toString());
			model.addAttribute("message", httpStatus.getReasonPhrase());
			model.addAttribute("timestamp", new Date());
			model.addAttribute("prevPage", referer);
			
		}
		return "/error/error";
		
	}
}
