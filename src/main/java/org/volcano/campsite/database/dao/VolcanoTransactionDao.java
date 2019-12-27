package org.volcano.campsite.database.dao;

import java.util.Date;
import java.util.List;

import org.volcano.campsite.database.entities.VolcanoTransactionEntity;

public interface VolcanoTransactionDao {
	
	public int saveVolcanoTransaction(VolcanoTransactionEntity volcanoTransactionEntity);

	public int removeVolcanoTransaction(String bookId);

	public VolcanoTransactionEntity findTransactionById(String bookId);

	public void updateVolcanoTransaction(VolcanoTransactionEntity volcanoTransactionEntity);

	public List<VolcanoTransactionEntity> getOverlappingReservations(Date dateFrom, Date dateTo);

	public List<VolcanoTransactionEntity> getReservations(Date dateFrom, Date dateTo); 

}
