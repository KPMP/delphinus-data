package org.kpmp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kpmp.slides.ParticipantRepository;
import org.kpmp.slides.Slide;
import org.kpmp.slides.SlideService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class GenerateLinkScriptTest {

    @Mock
    private ParticipantRepository participantRepository;
    private GenerateLinkScript generateLinkScript;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        generateLinkScript = new GenerateLinkScript(participantRepository);
    }

    @After
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
