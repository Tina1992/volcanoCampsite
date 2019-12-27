package org.volcano.campsite.responses;

import java.util.List;

import org.volcano.campsite.entities.DateRange;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class FreeRangesCampsiteResponse extends CampsiteResponse {
	
	private List<DateRange> dates;

	public FreeRangesCampsiteResponse(String resultValue, List<DateRange> dates) {
		super(resultValue);
		this.dates = dates;
	}

}
