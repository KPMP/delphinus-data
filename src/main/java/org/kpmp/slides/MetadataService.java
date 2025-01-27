package org.kpmp.slides;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataService {
    private ParticipantRepository participantRepository;

    @Autowired
    public MetadataService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public Metadata getMetadataForSlide(String kpmpId, String slideName) {
        Participant participant = participantRepository.findSlideByKpmpIdAndSlideName(kpmpId, slideName);
        if (participant != null && participant.getSlides() != null && !participant.getSlides().isEmpty()) {
            Map<String, List<Slide>> slideMap = participant.getSlides();
            for (Map.Entry<String, List<Slide>> entry : slideMap.entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    Slide slide = entry.getValue().get(0);
                    return slide.getMetadata();
                }
            }
        }
        return null;
    }
}
