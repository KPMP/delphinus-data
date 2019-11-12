package org.kpmp.slides;

import java.util.List;

import org.kpmp.logging.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SlideController {

	private SlideService slideService;
	private LoggingService logger;

	@Autowired
	public SlideController(SlideService slideService, LoggingService logger) {
		this.slideService = slideService;
		this.logger = logger;
	}

	@RequestMapping(value = "/v1/slides/{kpmpId}", method = RequestMethod.GET)
	public @ResponseBody List<Slide> getSlidesForParticipant(@PathVariable String kpmpId, HttpServletRequest request) {
		logger.logInfoMessage(this.getClass(),"Getting slides for participant " + kpmpId, request);
		return slideService.getSlidesForParticipant(kpmpId);
	}

	@RequestMapping(value = "/v1/slides", method = RequestMethod.GET)
	public @ResponseBody List<Participant> getAllParticipants(HttpServletRequest request) {
		logger.logInfoMessage(this.getClass(),"Getting all participants", request);
		return slideService.getAllParticipants();
	}

}
