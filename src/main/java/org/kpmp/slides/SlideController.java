package org.kpmp.slides;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SlideController {

	private SlideService slideService;

	@Autowired
	public SlideController(SlideService slideService) {
		this.slideService = slideService;
	}

	@RequestMapping(value = "/v1/slides/{kpmpId}", method = RequestMethod.GET)
	public @ResponseBody List<Slide> getSlidesForParticipant(@PathVariable String kpmpId) {
		return slideService.getSlidesForParticipant(kpmpId);
	}

	@RequestMapping(value = "/v1/slides", method = RequestMethod.GET)
	public @ResponseBody List<Participant> getAllParticipants() {
		return slideService.getAllParticipants();
	}

}
