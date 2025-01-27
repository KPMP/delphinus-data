package org.kpmp.slides;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends MongoRepository<Participant, String> {

	public Participant findByKpmpId(String kpmpId);

	@Query(fields = "{ 'kpmpId' : 1, 'label': 1 }")
	public List<Participant> findByOrderByKpmpIdAsc();

    @Query(value = "{ 'kpmp_id': ?0, 'slides.slideName': ?1 }", fields = "{ 'slides.$': 1 }")
    Participant findSlideByKpmpIdAndSlideName(String kpmpId, String slideName);

}
