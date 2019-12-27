package org.volcano.campsite.database.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.volcano.campsite.database.entities.VolcanoTransactionEntity;

public class VolcanoTransactionEntityRowMapper implements RowMapper<VolcanoTransactionEntity> {

	public VolcanoTransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		VolcanoTransactionEntity volcanoTransactionEntity = new VolcanoTransactionEntity();
		volcanoTransactionEntity.setId(rs.getInt("id"));
		volcanoTransactionEntity.setFullName(rs.getString("full_name"));
		volcanoTransactionEntity.setEmail(rs.getString("email"));
		volcanoTransactionEntity.setDateFrom(rs.getDate("date_from"));
		volcanoTransactionEntity.setDateTo(rs.getDate("date_to"));
		return volcanoTransactionEntity;
	}

}
