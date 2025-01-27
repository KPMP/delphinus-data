package org.kpmp.slides;

import java.util.List;

import org.kpmp.logging.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MetadataController {
    private MetadataService metadataService;
    private LoggingService logger;

    @Autowired
    public MetadataController(MetadataService metadataService, LoggingService logger) {
        this.metadataService = metadataService;
        this.logger = logger;
    }

    @RequestMapping(value="/v1/metadata/{kpmpId}/{slideName}", method = RequestMethod.GET)
    public @ResponseBody Metadata getMetadataForSlide (@PathVariable String kpmpId, @PathVariable String slideName, HttpServletRequest request) {
        logger.logInfoMessage(this.getClass(), "Getting metadata for slide " + slideName + " for participant " + kpmpId, request);
        return metadataService.getMetadataForSlide(kpmpId, slideName);
    }
}