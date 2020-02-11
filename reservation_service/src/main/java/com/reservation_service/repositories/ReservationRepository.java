package com.reservation_service.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reservation_service.models.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
	List<Reservation> findByBookId(int bookId);
	List<Reservation> findByDateLessThan(Date date);
	List<Reservation> deleteByDateLessThan(Date date);
}