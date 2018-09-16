package com.cvs.avocado.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cvs.avocado.models.ManagementPool;
import com.cvs.avocado.utils.InetAddressUtil;
import com.cvs.avocado.utils.OrchestratorConstants;

@Repository
public class ManagementPoolRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String TABLE_NAME =  "management_pool_tb";
	private static final String FIELDS_FOR_UPDATE = "management_IP_range_start, management_IP_range_end, modified_on";
	private static final String FIELDS_FOR_INSERT = "management_IP_range_start, management_IP_range_end, created_on, modified_on";
	private static final String FIND_IPV4_STATEMENT = "select 1 from "+ TABLE_NAME + " where inet_aton(?) between inet_aton(management_IP_range_start) and inet_aton(management_IP_range_end)";
	private static final String FIND_IPV6_STATEMENT = "select 1 from "+ TABLE_NAME + " where inet6_aton(?) between inet6_aton(management_IP_range_start) and inet6_aton(management_IP_range_end)";
	private static final String DEFAULT_SELECT_STATEMENT = "select id, " + FIELDS_FOR_INSERT + " from "+ TABLE_NAME;
	private static final String DEFAULT_INSERT_STATEMENT = "insert into "+ TABLE_NAME + " (" + FIELDS_FOR_INSERT + ") values(?, ?, ?, ?)";
	private static final String DEFAULT_UPDATE_STATEMENT = "update " + TABLE_NAME + " set " + FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where id = ?";
	private static final String DEFAULT_DELETE_STATEMENT = "delete from " + TABLE_NAME + " where id = ?";

	private RowMapper<ManagementPool> rowMapper = new ManagementPoolRowMapper();

	public List<ManagementPool> selectAllManagementPool() {
		List<ManagementPool> managementPoolList = null;
		try{
			managementPoolList = new ArrayList<ManagementPool>(jdbcTemplate.query(DEFAULT_SELECT_STATEMENT, rowMapper));
		} catch(DataAccessException ex){
			System.out.println(ex.toString());
		}
		return managementPoolList; 
	}

	public int findManagementIP(String managementIP) {
		int result = 0;
		try {
			if(InetAddressUtil.InetAddressType(managementIP) == OrchestratorConstants.InetIPV4Address) {
				result = jdbcTemplate.queryForObject(FIND_IPV4_STATEMENT, new Object[] {managementIP}, new int[] {java.sql.Types.VARCHAR}, Integer.class);
			} else if(InetAddressUtil.InetAddressType(managementIP) == OrchestratorConstants.InetIPV6Address) {
				result = jdbcTemplate.queryForObject(FIND_IPV6_STATEMENT, new Object[] {managementIP}, new int[] {java.sql.Types.VARCHAR}, Integer.class);
			}
		} catch (IncorrectResultSizeDataAccessException ex){
			System.out.println(ex.toString());
		}
		return result;
	}

	public int insertManagementPool(ManagementPool managementPool) {
		int result = 0;
		try{
			result = jdbcTemplate.update(DEFAULT_INSERT_STATEMENT, getFields(managementPool));
		} catch (DataAccessException ex){
			System.out.println(ex.toString());
		}
		return result;
	}

	public int updateManagementPool(ManagementPool managementPool) {
		int result = 0;
		try{
			result = jdbcTemplate.update(DEFAULT_UPDATE_STATEMENT, getFieldsForUpdate(managementPool));
		} catch (DataAccessException ex){
			System.out.println(ex.toString());
		}
		return result;
	}

	public int deleteManagementPool(String id) {
		int result = 0;
		try {
			result = jdbcTemplate.update(DEFAULT_DELETE_STATEMENT, id);
		} catch(DataAccessException ex) {
			System.out.println(ex.toString());
		}
		return result;
	}

	private static class ManagementPoolRowMapper implements RowMapper<ManagementPool>{

		@Override
		public ManagementPool mapRow(ResultSet rs, int rowNum) throws SQLException {
			ManagementPool managementPool = new ManagementPool();
			managementPool.setId(rs.getInt("id"));
			managementPool.setManagementIPRangeStart(rs.getString("management_IP_range_start"));
			managementPool.setManagementIPRangeEnd(rs.getString("management_IP_range_end"));
			managementPool.setCreatedOn(rs.getTimestamp("created_on"));
			managementPool.setModifiedOn(rs.getTimestamp("modified_on"));
			return managementPool;
		}

	}

	private Object[] getFields(ManagementPool managementPool) {
		return new Object[] {
				managementPool.getManagementIPRangeStart() != null ? managementPool.getManagementIPRangeStart() : null,
						managementPool.getManagementIPRangeEnd() != null ? managementPool.getManagementIPRangeEnd() : null,
								managementPool.getCreatedOn() != null ? managementPool.getCreatedOn() : new Timestamp(new Date().getTime()),
										managementPool.getModifiedOn() != null ? managementPool.getModifiedOn() : new Timestamp(new Date().getTime())
		};
	}

	private Object[] getFieldsForUpdate(ManagementPool managementPool) {
		return new Object[] {
				managementPool.getManagementIPRangeStart() != null ? managementPool.getManagementIPRangeStart() : null,
						managementPool.getManagementIPRangeEnd() != null ? managementPool.getManagementIPRangeEnd() : null,
								new Timestamp(new Date().getTime()),
								managementPool.getId()
		};
	}
}