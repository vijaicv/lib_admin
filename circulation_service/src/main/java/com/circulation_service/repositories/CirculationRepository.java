package com.circulation_service.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.circulation_service.models.Circulation;


@Repository
public interface CirculationRepository extends JpaRepository<Circulation, Integer>{
	Circulation findByUserIdAndBookId(int userid,int bookid);
	
}