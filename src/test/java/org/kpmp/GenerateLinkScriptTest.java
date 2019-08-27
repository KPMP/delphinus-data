package org.kpmp;

import org.junit.Test;
import org.kpmp.slides.Slide;

import static org.junit.Assert.assertEquals;

public class GenerateLinkScriptTest {

    GenerateLinkScript generateLinkScript;

    @Test
    public void testGetLinkConditional() {
        Slide slide = new Slide();
        slide.setId("1234");
        slide.setSlideName("Slide_Name");
        String actual = generateLinkScript.getLinkConditional("P1", slide, "STAIN");
        String expected  = "if ! [ -L P1_files ]; then\n" +
                "    ln -s ../knowledgeEnvironment/deepZoom/files_P1_STAIN/{2}_files 1234_files\n" +
                "    ln -s ../knowledgeEnvironment/deepZoom/files_P1_STAIN/{2}.dzi 1234.dzi\n" +
                "    ln -s ../knowledgeEnvironment/deepZoom/files_P1_STAIN/tn_{2}.jpeg tn_1234.jpeg\n" +
                "fi \n";
        assertEquals(expected, actual);
    }
}
