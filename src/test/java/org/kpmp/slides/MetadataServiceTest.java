package org.kpmp.slides;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MetadataServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private MetadataService metadataService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        metadataService = new MetadataService(participantRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        metadataService = null;
    }

    @Test
    public void testGetMetadataForSlide_Found() {
        String kpmpId = "testKpmpId";
        String slideName = "testSlideName";

        Metadata expectedMetadata = new Metadata();
        Slide slide = new Slide();
        slide.setSlideName(slideName);
        slide.setMetadata(expectedMetadata);

        Map<String, List<Slide>> slidesMap = new HashMap<>();
        slidesMap.put("someKey", Arrays.asList(slide));

        Participant participant = new Participant();
        participant.setSlides(slidesMap);

        when(participantRepository.findSlideByKpmpIdAndSlideName(kpmpId, slideName)).thenReturn(participant);

        Metadata result = metadataService.getMetadataForSlide(kpmpId, slideName);

        assertEquals(expectedMetadata, result);
    }

    @Test
    public void testGetMetadataForSlide_NotFound() {
        String kpmpId = "testKpmpId";
        String slideName = "testSlideName";

        Slide slide = new Slide();
        slide.setSlideName("otherSlideName");

        Map<String, List<Slide>> slidesMap = new HashMap<>();
        slidesMap.put("someKey", Arrays.asList(slide));

        Participant participant = new Participant();
        participant.setSlides(slidesMap);

        when(participantRepository.findSlideByKpmpIdAndSlideName(kpmpId, slideName)).thenReturn(participant);

        Metadata result = metadataService.getMetadataForSlide(kpmpId, slideName);

        assertNull(result);
    }

    @Test
    public void testGetMetadataForSlide_ParticipantNotFound() {
        String kpmpId = "testKpmpId";
        String slideName = "testSlideName";

        when(participantRepository.findSlideByKpmpIdAndSlideName(kpmpId, slideName)).thenReturn(null);

        Metadata result = metadataService.getMetadataForSlide(kpmpId, slideName);

        assertNull(result);
    }
}

