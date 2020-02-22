package com.reservation_service.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.reservation_service.models.Reservation;
import com.reservation_service.repositories.ReservationRepository;

@Service
public class TrashCleaningService {
	
	@Autowired
	ReservationRepository reservationRepo;
	
	
	@Scheduled(fixedDelay = 20000)
	public void deleteOldReservations() {
		System.out.println(">running trash cleaning service...");
		
		//get todays date
		Date today = new Date();
		//get calender obeject
		Calendar cal =new GregorianCalendar();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -30);
		
		
		List<Reservation> oldReservations = reservationRepo.findByDateLessThan(cal.getTime());
		
		if(oldReservations.size()>0) {
			for(Reservation r: oldReservations) {
				System.out.println("deleting reservation---> bookId="+r.getBookId()+" userId="+r.getUserId()+" date="+r.getDate());
			}
			reservationRepo.deleteAll(oldReservations);
		}
	}
}
