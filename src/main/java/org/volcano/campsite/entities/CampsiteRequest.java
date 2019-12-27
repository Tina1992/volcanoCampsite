package org.volcano.campsite.entities;

import java.util.Date;

import org.volcano.campsite.utils.CampsiteConstants;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CampsiteRequest {

	private UserData userData;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CampsiteConstants.DATE_FORMAT, timezone = CampsiteConstants.TIMEZONE)
	private Date dateFrom;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CampsiteConstants.DATE_FORMAT, timezone = CampsiteConstants.TIMEZONE)
	private Date dateTo;

}
