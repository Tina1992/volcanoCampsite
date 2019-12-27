package org.volcano.campsite.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultEnum {

	BA_VALID("Bookability successfull, B is possible"),
	BA_NOT_VALID_INCORRECT_DATES("Boookability not successful. Please, double check dates in the request."),
	BA_NOT_VALID_TOO_LONG("Boookability not successful. Staying for more than 3 days is not possible"),
	BA_NOT_VALID_NOT_ENOUGH_TIME("Boookability not successful. You need one day in advance to book"),
	BA_NOT_VALID_TOO_MUCH_TIME("Boookability not successful. Only bookings in one month are available"),
	BA_NOT_VALID_NOT_AVAILABILITY("Bookability not succesfful. All the campsite is full for the times requested"),
	FREE_VALID("Data of free dates is available"),
	FREE_NOT_VALID("Data of free dates is not available. Please, communicate with the site to ensure that everything is right"),
	B_VALID("Booking done correctly"),
	B_NOT_VALID("Booking error. Communicate with the site to ensure that everything is right"),
	C_NOT_VALID("Error cancelling the reservation. Please, communicate with the site to ensure that everything is right"),
	C_VALID("Cancel done correctly"),
	U_NOT_VALID("Error updating the reservation. Please, communicate with the site to ensure that everything is right"),
	U_VALID("Update done correctly");
	
	private String result;
	
}
