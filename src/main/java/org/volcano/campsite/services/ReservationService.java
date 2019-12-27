package org.volcano.campsite.services;

import java.util.Date;
import java.util.List;

import org.volcano.campsite.controllers.ResultEnum;
import org.volcano.campsite.entities.DateRange;
import org.volcano.campsite.entities.UserData;

public interface ReservationService {
	
	public ResultEnum checkReservation(Date dateFrom, Date dateTo);

	public String bookReservation(UserData userData, Date dateFrom, Date dateTo);

	public void cancelReservation(String bookId) throws Exception;

	public void updateReservation(String bookId, UserData userData, Date dateFrom, Date dateTo);
	
	public List<DateRange> getFreeDates(Date dateFrom, Date dateTo);

}
