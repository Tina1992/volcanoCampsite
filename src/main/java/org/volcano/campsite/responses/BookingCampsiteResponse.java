package org.volcano.campsite.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class BookingCampsiteResponse extends CampsiteResponse {

	private String bookingId;
	
	public BookingCampsiteResponse(String resultValue, String bookingId) {
		super(resultValue);
		this.bookingId = bookingId;
	}

}
