package org.volcano.campsite.database.services;

import java.util.Date;
import java.util.List;

import org.volcano.campsite.database.entities.VolcanoTransactionEntity;
import org.volcano.campsite.entities.UserData;

public interface ReservationDatabaseService {

	public int saveVolcanoTransaction(UserData userData, Date dateFrom, Date dateTo); 

	public void removeVolcanoTransaction(String bookId) throws Exception;

	public void updateVolcanoTransaction(String bookId, UserData userData, Date dateFrom, Date dateTo);

	public List<VolcanoTransactionEntity> getOverlappingReservations(Date dateFrom, Date dateTo);

	public List<VolcanoTransactionEntity> getReservations(Date dateFrom, Date dateTo); 
	
}
