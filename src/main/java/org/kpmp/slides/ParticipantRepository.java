package org.kpmp.slides;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends MongoRepository<Participant, String> {

	public Participant findByKpmpId(String kpmpId);
	public List<Participant> findByOrderByKpmpIdAsc();

}
