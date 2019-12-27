package org.volcano.campsite.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.volcano.campsite.controllers.ResultEnum;
import org.volcano.campsite.database.services.ReservationDatabaseService;
import org.volcano.campsite.entities.DateRange;
import org.volcano.campsite.entities.UserData;
import org.volcano.campsite.utils.CampsiteConstants;

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationDatabaseService reservationDatabaseService;

	public ResultEnum checkReservation(Date dateFrom, Date dateTo) {
		Date today = new Date();
		if (dateFrom.after(dateTo) || today.after(dateFrom) || today.after(dateTo)) {
			return ResultEnum.BA_NOT_VALID_INCORRECT_DATES;
		}
		if (Math.abs(ChronoUnit.DAYS.between(dateFrom.toInstant(),
				dateTo.toInstant())) > CampsiteConstants.MAX_DAYS_STAY_ALLOWED) {
			return ResultEnum.BA_NOT_VALID_TOO_LONG;
		}
		if (Math.abs(ChronoUnit.DAYS.between(dateFrom.toInstant(),
				today.toInstant())) < CampsiteConstants.MIN_DAYS_IN_ADVANCE_FOR_RESERVATION) {
			return ResultEnum.BA_NOT_VALID_NOT_ENOUGH_TIME;
		}
		SimpleDateFormat format = new SimpleDateFormat(CampsiteConstants.DATE_FORMAT);

		if (Math.abs(ChronoUnit.MONTHS.between(LocalDate.parse(format.format(dateFrom)).withDayOfMonth(1),
				LocalDate.parse(format.format(today))
						.withDayOfMonth(1))) > CampsiteConstants.MAX_MONTH_IN_ADVANCE_FOR_RESERVATION) {
			return ResultEnum.BA_NOT_VALID_TOO_MUCH_TIME;
		}

		if (reservationDatabaseService.getOverlappingReservations(dateFrom, dateTo).size() > 0) {
			return ResultEnum.BA_NOT_VALID_NOT_AVAILABILITY;
		}

		return ResultEnum.BA_VALID;
	}

	public String bookReservation(UserData userData, Date dateFrom, Date dateTo) {
		return String.valueOf(reservationDatabaseService.saveVolcanoTransaction(userData, dateFrom, dateTo));
	}

	public void cancelReservation(String bookId) throws Exception {
		reservationDatabaseService.removeVolcanoTransaction(bookId);
	}

	public void updateReservation(String bookId, UserData userData, Date dateFrom, Date dateTo) {
		reservationDatabaseService.updateVolcanoTransaction(bookId, userData, dateTo, dateTo);

	}

	public List<DateRange> getFreeDates(Date dateFrom, Date dateTo) {
		List<DateRange> reservations = reservationDatabaseService.getReservations(dateFrom, dateTo).stream()
				.map(reservation -> new DateRange(reservation.getDateFrom(), reservation.getDateTo()))
				.collect(Collectors.toList());

		DateRange range = new DateRange(dateFrom, dateTo);
		return range.substractRange(reservations);
	}

}
