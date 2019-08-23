package org.kpmp.slides;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlideService {

	private PatientSlidesRepository patientRepo;

	@Autowired
	public SlideService(PatientSlidesRepository patientRepo) {
		this.patientRepo = patientRepo;
	}

	public List<Slide> getSlidesForPatient(String kpmpId) {
		PatientSlides patient = patientRepo.findByKpmpId(kpmpId);
		if (patient != null) {
			return patient.getSlides();
		}
		return Collections.emptyList();
	}

	public List<PatientSlides> getAllPatientSlides() {
		List<PatientSlides> patientSlides = patientRepo.findByOrderByKpmpIdAsc();
		if (patientSlides != null) {
			return patientSlides;
		}
		return Collections.emptyList();
	}
}
