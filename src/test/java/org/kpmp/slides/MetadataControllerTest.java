package org.kpmp.slides;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kpmp.logging.LoggingService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;

public class MetadataControllerTest {
    @Mock
    private MetadataService metadataService;

    @Mock
    private LoggingService logger;
    private MetadataController controller;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new MetadataController(metadataService, logger);
    }

    @AfterEach
    public void tearDown() throws Exception {
        controller = null;
    }

    @Test
    public void testGetMetadataForSlide_Found() {
        String kpmpId = "testKpmpId";
        String slideName = "testSlideName";
        Metadata metadata = new Metadata();

        when(metadataService.getMetadataForSlide(kpmpId, slideName)).thenReturn(metadata);
        HttpServletRequest request = mock(HttpServletRequest.class);

        Metadata result = controller.getMetadataForSlide(kpmpId, slideName, request);

        verify(logger).logInfoMessage(controller.getClass(), "Getting metadata for slide " + slideName + " for participant " + kpmpId, request);
        assertEquals(metadata, result);
    }

    @Test
    public void testGetMetadataForSlide_NotFound() {
        String kpmpId = "testKpmpId";
        String slideName = "testSlideName";

        when(metadataService.getMetadataForSlide(kpmpId, slideName)).thenReturn(null);
        HttpServletRequest request = mock(HttpServletRequest.class);

        Metadata result = controller.getMetadataForSlide(kpmpId, slideName, request);

        verify(logger).logInfoMessage(controller.getClass(), "Getting metadata for slide " + slideName + " for participant " + kpmpId, request);
        assertEquals(null, result);
    }
}
