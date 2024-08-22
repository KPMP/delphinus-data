package org.kpmp.slides;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlideService {

	private ParticipantRepository participantRepository;

	@Autowired
	public SlideService(ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}

	public Map<String, List<Slide>> getSlidesForParticipant(String kpmpId) {
		Participant participant = participantRepository.findByKpmpId(kpmpId);
		if (participant != null) {
			return participant.getSlides();
		}
		return Collections.emptyMap();
	}

	public List<Participant> getAllParticipants() {
		List<Participant> participants = participantRepository.findByOrderByKpmpIdAsc();
		if (participants != null) {
			return participants;
		}
		return Collections.emptyList();
	}
}
