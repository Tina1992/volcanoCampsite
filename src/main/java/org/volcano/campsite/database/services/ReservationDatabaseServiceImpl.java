package org.volcano.campsite.database.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.volcano.campsite.database.dao.VolcanoTransactionDao;
import org.volcano.campsite.database.entities.VolcanoTransactionEntity;
import org.volcano.campsite.entities.UserData;

@Service("reservationDatabaseService")
public class ReservationDatabaseServiceImpl implements ReservationDatabaseService {

	@Autowired
	private VolcanoTransactionDao volcanoTransactionDao;

	public int saveVolcanoTransaction(UserData userData, Date dateFrom, Date dateTo) {
		VolcanoTransactionEntity volcanoTransactionEntity = new VolcanoTransactionEntity();
		volcanoTransactionEntity.setDateFrom(dateFrom);
		volcanoTransactionEntity.setDateTo(dateTo);
		volcanoTransactionEntity.setEmail(userData.getMail());
		volcanoTransactionEntity.setFullName(userData.getFullName());
		return volcanoTransactionDao.saveVolcanoTransaction(volcanoTransactionEntity);

	}

	public void removeVolcanoTransaction(String bookId) throws Exception {
		if (volcanoTransactionDao.removeVolcanoTransaction(bookId) < 1) {
			throw new Exception("Error removing transactions. No row was removed.");
		}
	}

	public void updateVolcanoTransaction(String bookId, UserData userData, Date dateFrom, Date dateTo) {
		VolcanoTransactionEntity volcanoTransactionEntity = volcanoTransactionDao.findTransactionById(bookId);
		volcanoTransactionEntity.setEmail(userData.getMail());
		volcanoTransactionEntity.setFullName(userData.getFullName());
		volcanoTransactionEntity.setDateFrom(dateFrom);
		volcanoTransactionEntity.setDateTo(dateTo);
		volcanoTransactionDao.updateVolcanoTransaction(volcanoTransactionEntity);
	}

	public List<VolcanoTransactionEntity> getOverlappingReservations(Date dateFrom, Date dateTo) {
		return volcanoTransactionDao.getOverlappingReservations(dateFrom, dateTo);
	}

	@Override
	public List<VolcanoTransactionEntity> getReservations(Date dateFrom, Date dateTo) {
		return volcanoTransactionDao.getReservations(dateFrom, dateTo);
	}

}
