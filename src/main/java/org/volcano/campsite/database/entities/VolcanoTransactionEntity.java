package org.volcano.campsite.database.entities;

import java.util.Date;

import lombok.Data;

@Data
public class VolcanoTransactionEntity {
	
	private int id;
	private String fullName;
	private String email;
	private Date dateFrom;
	private Date dateTo;
}
