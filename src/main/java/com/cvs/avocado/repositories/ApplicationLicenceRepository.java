package com.cvs.avocado.repositories;

import java.sql.BatchUpdateException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cvs.avocado.models.Application;
import com.cvs.avocado.models.ApplicationLicence;

@Repository
public class ApplicationLicenceRepository {
	
	private static final Logger log = LogManager.getLogger();

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String TABLE_NAME = "application_licence_tb";
	private static final String FIELDS_FOR_UPDATE = "is_sent, is_registered, updated_on ";
	private static final String FIELDS_FOR_INSERT = "app_id, management_IP, created_on, " + FIELDS_FOR_UPDATE;
	private static final String CHECK_APP_ID_PRESENCE_BY_MANAGEMENT_IP_STATEMENT  = "select 1 from " + TABLE_NAME + " where management_IP = (?)";
	private static final String SELECT_BY_APP_ID_MANAGEMENT_IP_STATEMENT = "select " + FIELDS_FOR_INSERT + " from " + TABLE_NAME + " where app_id = (?) and management_IP = (?)";
	private static final String DEFAULT_INSERT_OR_UPDATE_STATEMENT = "insert into "+ TABLE_NAME + " (" + FIELDS_FOR_INSERT +") values (?, ?, ?, ?, ?, ?) on duplicate key update " + FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=?";
	private static final String DEFAULT_UPDATE_STATEMENT = "update " + TABLE_NAME + "set " + FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where app_id = (?)";

	public ApplicationLicence getLicenceByAppIdManagementIP(int appId, String managementIP){
		ApplicationLicence appLicence = null;
		try {
			appLicence = jdbcTemplate.queryForObject(SELECT_BY_APP_ID_MANAGEMENT_IP_STATEMENT, new Object[] {appId, managementIP}, new ApplicationLicenceRowMapper());
		} catch(DataAccessException ex) {
			System.out.println(ex.toString());
		}
		return appLicence;
	}



	public int insertOrUpdateApplicationLicence(ApplicationLicence appLicence) {
		int result = 0;
		try{
			result = jdbcTemplate.update(DEFAULT_INSERT_OR_UPDATE_STATEMENT, getFieldsForInsertOrUpdate(appLicence));
		} catch (DataAccessException ex){
			System.out.println(ex.toString());
		}
		return result;
	}

	public List<Application> updateApplicationLicence(List<Application> applicationList) {
		
		ApplicationLicence appLicence = null;
		try {
			List<Object[]> batch = new ArrayList<Object[]>();
			for(Application application : applicationList) {
				appLicence = new ApplicationLicence();
				appLicence.setAppId(application.getAppId());
				appLicence.setSent(false);
				appLicence.setRegistered(true);
				batch.add(getFieldsForUpdate(appLicence));
			}
			
			jdbcTemplate.batchUpdate(DEFAULT_UPDATE_STATEMENT, batch);
			
		} catch (DataAccessException ex) {
			Throwable rootCause = ex.getRootCause();
			if(rootCause instanceof BatchUpdateException) {
				BatchUpdateException bue = (BatchUpdateException)rootCause;
				int[] updateCount = bue.getUpdateCounts();
				for(int count : updateCount) {
					if(count == Statement.EXECUTE_FAILED) {
						log.error("Failed to update licence of following application: " + applicationList.get(count).toLog());
						applicationList.remove(count);
					}
				}
			}
			log.error("Error occured while updating licence of applications. Reason: " + ex.getMessage());
			applicationList = null;
		}
		return applicationList;
	}
	
	public boolean checkAppIdPresenceByManagementIP(String managementIP) {
		boolean result = false;
		try {
			result = jdbcTemplate.queryForObject(CHECK_APP_ID_PRESENCE_BY_MANAGEMENT_IP_STATEMENT, Boolean.class, new Object[] {managementIP});
		} catch(DataAccessException ex) {
			System.out.println(ex.toString());
		}
		return result;
	}

	private Object[] getFieldsForInsertOrUpdate(ApplicationLicence appLicence) {
		return new Object[] {
				appLicence.getAppId(),
				appLicence.getManagementIP(),
				new Timestamp(new Date().getTime()),
				false, false,
				new Timestamp(new Date().getTime()),
				appLicence.getSent(),
				appLicence.getRegistered(),
				new Timestamp(new Date().getTime())			
		};
	}
	
	private Object[] getFieldsForUpdate(ApplicationLicence appLicence) {
		return new Object[] {
				appLicence.getSent(),
				appLicence.getRegistered(),
				new Timestamp(new Date().getTime()),
				appLicence.getAppId()
		};
	}

	private static class ApplicationLicenceRowMapper implements RowMapper<ApplicationLicence> {

		@Override
		public ApplicationLicence mapRow(ResultSet rs, int rowNum) throws SQLException {

			ApplicationLicence appLicence = new ApplicationLicence();
			appLicence.setAppId(rs.getInt("app_id"));
			appLicence.setManagementIP(rs.getString("management_IP"));
			appLicence.setSent(rs.getBoolean("is_sent"));
			appLicence.setRegistered(rs.getBoolean("is_registered"));
			appLicence.setCreatedOn(rs.getTimestamp("created_on"));
			appLicence.setUpdatedOn(rs.getTimestamp("updated_on"));
			return appLicence;
		}

	}

}
