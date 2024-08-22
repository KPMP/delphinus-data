package org.kpmp;

import org.kpmp.slides.Participant;
import org.kpmp.slides.ParticipantRepository;
import org.kpmp.slides.Slide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.ComponentScan;
import org.kpmp.logging.LoggingService;

import java.io.File;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.util.List;

@ComponentScan(basePackages = { "org.kpmp" })
public class GenerateLinkScript implements CommandLineRunner {

	private LoggingService logger;
    private ParticipantRepository participantRepository;
    private static final String HEADER = "#!/bin/bash\n" +
            "####\n" +
            "# link.sh\n" +
            "#### \n" +
            "#\n" +
            "# Creates Deep Zoom asset symlinks into the knowledge environment, associating DZ files by the package file ID given when svs2dz job was run.\n" +
            "# This command must be run in the folder where you want the links created.\n" +
            "#\n";
    private static final String LINK_CONDITIONAL_FORMAT = "if ! [ -L {0}_files ]; then\n" +
            "    ln -s ../knowledgeEnvironment/deepZoom/files_{1}_{3}/{2}_files {0}_files\n" +
            "    ln -s ../knowledgeEnvironment/deepZoom/files_{1}_{3}/{2}.dzi {0}.dzi\n" +
            "    ln -s ../knowledgeEnvironment/deepZoom/files_{1}_{3}/tn_{2}.jpeg tn_{0}.jpeg\n" +
            "fi \n";

    @Autowired
    public GenerateLinkScript(ParticipantRepository participantRepository, LoggingService logger) {
        this.participantRepository = participantRepository;
		this.logger = logger;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GenerateLinkScript.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Participant> participantList = participantRepository.findByOrderByKpmpIdAsc();
        FileOutputStream linkScript = new FileOutputStream(new File("link.sh"));
        linkScript.write(HEADER.getBytes());
        for (Participant participant: participantList) {
            participant.getSlides().forEach((slideType, slideList) -> {
                for (Slide slide : slideList) {
                    String linkCondition = getLinkConditional(participant.getKpmpId(), slide, "PAS");
                    try {
                        linkScript.write(linkCondition.getBytes());
                    }
                    catch(Exception e) {
                        logger.logErrorMessage(this.getClass(), e.toString(), null);
                    }
                }
            });
        }
        linkScript.close();
    }

    public String getLinkConditional(String participantId, Slide slide, String stain) {
        return MessageFormat.format(LINK_CONDITIONAL_FORMAT, slide.getId(), participantId, slide.getSlideName(), stain);
    }
}