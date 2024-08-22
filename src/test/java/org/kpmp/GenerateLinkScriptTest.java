package org.kpmp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kpmp.slides.ParticipantRepository;
import org.kpmp.slides.Slide;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.kpmp.logging.LoggingService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerateLinkScriptTest {

    @Mock
    private ParticipantRepository participantRepository;
	@Mock
	private LoggingService loggingService;
    private GenerateLinkScript generateLinkScript;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        generateLinkScript = new GenerateLinkScript(participantRepository, loggingService);
    }

    @AfterEach
    public void tearDown() throws Exception {
        generateLinkScript = null;
    }

    @Test
    public void testGetLinkConditional() {
        Slide slide = new Slide();
        slide.setId("1234");
        slide.setSlideName("Slide_Name");
        String actual = generateLinkScript.getLinkConditional("P1", slide, "STAIN");
        String expected  = "if ! [ -L 1234_files ]; then\n" +
                "    ln -s ../knowledgeEnvironment/deepZoom/files_P1_STAIN/Slide_Name_files 1234_files\n" +
                "    ln -s ../knowledgeEnvironment/deepZoom/files_P1_STAIN/Slide_Name.dzi 1234.dzi\n" +
                "    ln -s ../knowledgeEnvironment/deepZoom/files_P1_STAIN/tn_Slide_Name.jpeg tn_1234.jpeg\n" +
                "fi \n";
        assertEquals(expected, actual);
    }
}
