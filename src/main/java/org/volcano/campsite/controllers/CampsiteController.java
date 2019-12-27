package org.volcano.campsite.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.volcano.campsite.entities.CampsiteRequest;
import org.volcano.campsite.responses.BookingCampsiteResponse;
import org.volcano.campsite.responses.CampsiteResponse;
import org.volcano.campsite.responses.FreeRangesCampsiteResponse;
import org.volcano.campsite.services.ReservationService;
import org.volcano.campsite.utils.CampsiteConstants;

@RestController
@RequestMapping(value = "/reservations")
public class CampsiteController {

	@Autowired
	private ReservationService reservationService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CampsiteResponse getFreeDateRanges(
			@RequestParam("dateFrom") @DateTimeFormat(pattern = CampsiteConstants.DATE_FORMAT) Optional<Date> dateFrom,
			@RequestParam("dateTo") @DateTimeFormat(pattern = CampsiteConstants.DATE_FORMAT) Optional<Date> dateTo) {
		Date dateFromD = dateFrom.orElse(new Date());
		Calendar c = Calendar.getInstance();
		c.setTime(dateFromD);
		c.add(Calendar.MONTH, 1);
		Date dateToD = dateTo.orElse(c.getTime());
		try {
			return new FreeRangesCampsiteResponse(ResultEnum.FREE_VALID.getResult(),
					reservationService.getFreeDates(dateFromD, dateToD));
		} catch (Exception e) {
			e.printStackTrace();
			return new CampsiteResponse(ResultEnum.FREE_NOT_VALID.getResult());
		}
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public CampsiteResponse bookReservation(@RequestBody CampsiteRequest campsiteRequest) {
		try {
			ResultEnum baResult = reservationService.checkReservation(campsiteRequest.getDateFrom(),
					campsiteRequest.getDateTo());
			if (baResult.equals(ResultEnum.BA_VALID)) {
				String bookingId = reservationService.bookReservation(campsiteRequest.getUserData(),
						campsiteRequest.getDateFrom(), campsiteRequest.getDateTo());
				return new BookingCampsiteResponse(ResultEnum.B_VALID.getResult(), bookingId);
			} else {
				return new CampsiteResponse(baResult.getResult());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new CampsiteResponse(ResultEnum.B_NOT_VALID.getResult());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public CampsiteResponse cancelReservation(@PathVariable("id") String id) {
		try {
			reservationService.cancelReservation(id);
			return new CampsiteResponse(ResultEnum.C_VALID.getResult());
		} catch (Exception e) {
			e.printStackTrace();
			return new CampsiteResponse(ResultEnum.C_NOT_VALID.getResult());
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public CampsiteResponse updateReservation(@PathVariable String id,
			@RequestBody CampsiteRequest campsiteRequest) {
		try {
			reservationService.updateReservation(id, campsiteRequest.getUserData(), campsiteRequest.getDateFrom(),
					campsiteRequest.getDateTo());
			return new CampsiteResponse(ResultEnum.U_VALID.getResult());
		} catch (Exception e) {
			e.printStackTrace();
			return new CampsiteResponse(ResultEnum.U_NOT_VALID.getResult());
		}
	}

}
