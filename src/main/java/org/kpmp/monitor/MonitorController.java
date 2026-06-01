package org.kpmp.monitor;

import org.kpmp.logging.LoggingService;
import org.kpmp.slides.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MonitorController {

    private SlideService slideService;
    private LoggingService logger;

    @Autowired
    public MonitorController(SlideService slideService, LoggingService logger) {
        this.slideService = slideService;
        this.logger = logger;
    }

    @RequestMapping(value = "/v1/status", method = RequestMethod.GET)
    public @ResponseBody String getStatus(HttpServletRequest request) {
        String status = "Status OK. " + slideService.getAllParticipants().size() + " participants found.";
        logger.logInfoMessage(this.getClass(), "Getting status", request);
        return status;
    }
}
