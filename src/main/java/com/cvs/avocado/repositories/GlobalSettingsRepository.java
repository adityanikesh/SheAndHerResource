package com.cvs.avocado.repositories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalSettingsRepository {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static final String TABLE_NAME = "global_settings_tb";
	public static final String DISCOVER_ONLY_MODE_COLUMN = "discover_only_mode";
	public static final String GET_DISCOVER_ONLY_MODE_STATEMENT = "select settings_value from " + TABLE_NAME +" where settings_key = 'discover_only_mode'";
	public static final String GET_ORCH_URL_STATEMENT = "select settings_value from " + TABLE_NAME +" where settings_key = 'orch_url'";
	public static final String GET_PROCESS_INTERVAL_STATEMENT = "select settings_value from " + TABLE_NAME + " where settings_key = 'process_interval'";
	public static final String INSERT_PROCESS_INTERVAL_STATEMENT = "update " + TABLE_NAME + "set settings_value = ? where settings_key = 'process_interval'";

	public boolean getDiscoverOnlyMode() {
		String mode = "";
		try {
			mode = jdbcTemplate.queryForObject(GET_DISCOVER_ONLY_MODE_STATEMENT, String.class);
		} catch(DataException ex) {
			log.error("Error occured while querying for the discovery mode. Reason: " + ex.getMessage());
		}
		if (mode.equals("true")){
			return true;
		}else {
			return false;
		}
	}

	public String getOrchestratorURL() {
		String orchURL = "";
		try {
			orchURL = jdbcTemplate.queryForObject(GET_ORCH_URL_STATEMENT, String.class);
		} catch(DataException ex) {
			log.error("Error occured while querying for the orchestrator url. Reason: " + ex.getMessage());
		}
		return orchURL;
	}
	
	public int getProcessInterval() {
		int processInterval = 120;
		try {
			processInterval = jdbcTemplate.queryForObject(GET_PROCESS_INTERVAL_STATEMENT, Integer.class);
		} catch(DataException ex) {
			log.error("Error occured while querying for the process interval. Reason: " + ex.getMessage());
		}
		return processInterval;
	}
	
	public boolean updatePollinInterval(int pollingInterval) {
		int rowsAffected = 0;
		try {
			rowsAffected = jdbcTemplate.update(INSERT_PROCESS_INTERVAL_STATEMENT, new Object[pollingInterval], new int[java.sql.Types.INTEGER]);
		} catch(DataAccessException ex) {
			log.error("Error occured while executing the query to update polling interval. Reason: " + ex.getMessage());
		}
		
		if(rowsAffected>0) {
			return true;
		} else {
			return false;
		}
	}
}
