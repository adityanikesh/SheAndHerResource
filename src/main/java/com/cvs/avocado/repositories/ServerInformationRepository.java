package com.cvs.avocado.repositories;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cvs.avocado.models.ServerInformation;

@Repository
public class ServerInformationRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final Logger log = LogManager.getLogger();

	private static final String TABLE_NAME =  "server_information_tb";
	private static final String FIELDS_FOR_UPDATE = "isActive, updated_on";
	private static final String FIELDS_FOR_INSERT = "management_IP, server_IP, isActive, created_on, updated_on";
	private static final String GET_SERVER_STATUS_STATEMENT = "select count(1) from " + TABLE_NAME + " where management_IP = ? and isActive = 1";
	private static final String DEFAULT_INSERT_OR_UPDATE_STATEMENT = "insert into "+ TABLE_NAME + " (" + FIELDS_FOR_INSERT +") values (?, ?, ?, ?, ?) on duplicate key update " + FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=?";
	private static final String DEFAULT_DELETE_STATEMENT = "delete from " + TABLE_NAME + " where management_IP = ?";
	private static final String GET_ACTIVE_MANAGEMENT_SERVERS_LIST_STATEMENT = "select management_IP from " + TABLE_NAME + " where isActive = 1";
		
	public boolean getServerStatus(String managementIP) {
		boolean result = false;
		try {
			result = jdbcTemplate.queryForObject(GET_SERVER_STATUS_STATEMENT, Boolean.class, new Object[]{managementIP});
		} catch (DataAccessException ex){
			System.out.println(ex.toString());
		}
		return result;
	}
	
	public List<String> getActiveManagementServersList(){
		
		List<String> managementServersList = null;
		
		try {
			managementServersList = jdbcTemplate.queryForList(GET_ACTIVE_MANAGEMENT_SERVERS_LIST_STATEMENT, String.class);
		} catch(DataAccessException ex) {
			log.error("Error occured while fetching the active management servers list: Reason: " + ex.getMessage());
		}
		return managementServersList;
	}
	
	public int insertOrUpdateServerInformation(ServerInformation serverInformation) {
		int result = 0;
		try{
			result = jdbcTemplate.update(DEFAULT_INSERT_OR_UPDATE_STATEMENT, getFieldsForInserOrUpdate(serverInformation));
		} catch (DataAccessException ex){
			System.out.println(ex.toString());
		}
		return result;
	}
	
	public int deleteServerStatusInformation(String managementIP) {
		int result = 0;
		try {
			result = jdbcTemplate.update(DEFAULT_DELETE_STATEMENT, managementIP);
		} catch(DataAccessException ex) {
			System.out.println(ex.toString());
		}
		return result;
	}

	private Object[] getFieldsForInserOrUpdate(ServerInformation serverInformation) {
		return new Object[] {
				serverInformation.getManagementIP(),
				serverInformation.getServerIP(),
				serverInformation.getStatus(),
				new Timestamp(new Date().getTime()),
				new Timestamp(new Date().getTime()),
				serverInformation.getStatus(),
				new Timestamp(new Date().getTime())
		};
	}
}
