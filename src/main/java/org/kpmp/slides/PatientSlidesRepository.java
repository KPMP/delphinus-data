package org.kpmp.slides;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientSlidesRepository extends MongoRepository<PatientSlides, String> {

	public PatientSlides findByKpmpId(String kpmpId);

}
