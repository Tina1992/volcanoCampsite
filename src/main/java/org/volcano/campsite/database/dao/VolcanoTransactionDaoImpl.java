package org.volcano.campsite.database.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.volcano.campsite.database.entities.VolcanoTransactionEntity;
import org.volcano.campsite.database.rowmappers.VolcanoTransactionEntityRowMapper;

@Component("volcanoTransactionDao")
public class VolcanoTransactionDaoImpl implements VolcanoTransactionDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public int saveVolcanoTransaction(VolcanoTransactionEntity volcanoTransactionEntity) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("volcano_reservation").usingGeneratedKeyColumns("id");
		Map<String, Object> parameters = new HashMap<String, Object>(4);
		parameters.put("full_name", volcanoTransactionEntity.getFullName());
		parameters.put("email", volcanoTransactionEntity.getEmail());
		parameters.put("date_from", volcanoTransactionEntity.getDateFrom());
		parameters.put("date_to", volcanoTransactionEntity.getDateTo());
		Number insertedId = simpleJdbcInsert.executeAndReturnKey(parameters);
		return insertedId.intValue();
	}

	@Override
	public int removeVolcanoTransaction(String bookId) {
		String SQL = "delete from volcano_reservation where id = ?";
		return jdbcTemplate.update(SQL, bookId);
	}

	@Override
	public VolcanoTransactionEntity findTransactionById(String bookId) {
		String sql = "SELECT * FROM volcano_reservation WHERE ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { bookId }, new VolcanoTransactionEntityRowMapper());
	}

	@Transactional
	@Override
	public void updateVolcanoTransaction(VolcanoTransactionEntity volcanoTransactionEntity) {
		String sql = "UPDATE volcano_reservation SET full_name = ? AND email = ? AND date_from = ? AND date_to = ? WHERE ID = ?";
		jdbcTemplate.update(sql, volcanoTransactionEntity.getFullName(), volcanoTransactionEntity.getEmail(),
				volcanoTransactionEntity.getDateFrom(), volcanoTransactionEntity.getDateTo(),
				volcanoTransactionEntity.getId());

	}

	@Override
	public List<VolcanoTransactionEntity> getOverlappingReservations(Date dateFrom, Date dateTo) {
		String sql = "SELECT * FROM volcano_reservation WHERE ((date_from < ? AND ? < date_to) OR (date_from < ? AND ? < date_to)) OR (date_from = ?) OR (date_to = ?)";
		return jdbcTemplate.query(sql,
				new Object[] { new java.sql.Date(dateFrom.getTime()), new java.sql.Date(dateFrom.getTime()),
						new java.sql.Date(dateTo.getTime()), new java.sql.Date(dateTo.getTime()),
						new java.sql.Date(dateFrom.getTime()), new java.sql.Date(dateTo.getTime()) },
				new VolcanoTransactionEntityRowMapper());
	}

	@Override
	public List<VolcanoTransactionEntity> getReservations(Date dateFrom, Date dateTo) {
		String sql = "SELECT * FROM volcano_reservation WHERE (date_from >= ?) OR (date_to <= ?) OR (date_from < ? and date_to > ?)";
		return jdbcTemplate.query(sql,
				new Object[] { new java.sql.Date(dateFrom.getTime()), new java.sql.Date(dateTo.getTime()),
						new java.sql.Date(dateFrom.getTime()), new java.sql.Date(dateTo.getTime()) },
				new VolcanoTransactionEntityRowMapper());
	}

}
