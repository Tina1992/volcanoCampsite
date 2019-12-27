package org.volcano.campsite.entities;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.volcano.campsite.utils.CampsiteConstants;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateRange {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CampsiteConstants.DATE_FORMAT)
	private Date dateFrom;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CampsiteConstants.DATE_FORMAT)
	private Date dateTo;

	public List<DateRange> substractRange(List<DateRange> dateRanges) {
		Range<Date> me = Range.closed(dateFrom, dateTo);

		RangeSet<Date> result = TreeRangeSet.create();
		result.add(me);
		for (DateRange dateRange : dateRanges) {
			result.remove(Range.closed(dateRange.getDateFrom(), dateRange.getDateTo()));
		}
		return result.asDescendingSetOfRanges().stream().map(range -> {
			return new DateRange(range.lowerEndpoint(), range.upperEndpoint());
		}).collect(Collectors.toList());
	}

}
