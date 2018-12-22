package com.cvs.avocado.repositories;

import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.cvs.avocado.models.Application;

@Repository
public class ApplicationRepository {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String TABLE_NAME =  "application_tb";
	private static final String FIELDS_FOR_INSERT = "app_name, alias_app_name, app_path, check_sum, sha256, cid, did, adpl_app_id, is_adpl_enabled, is_malware, is_registered, app_image_id, management_IP, created_on, updated_on";
	private static final String FIELDS_FOR_UPDATE = "alias_app_name, cid, did, adpl_app_id, is_adpl_enabled, is_malware, is_registered, app_image_id, updated_on";
	private static final String SLA_CHECK_SP = "sla_check";
	private static final String REGISTER_APPLICATION_SP = "call register_application_SP (?, ?, ?, ?, ?, ?, ?)";
	private static final String APP_LIC_FILE_GEN_SP = "app_lic_file_gen_SP";
	private static final String DEFAULT_INSERT_STATEMENT = "insert into "+ TABLE_NAME + " (" + FIELDS_FOR_INSERT + ") values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String DEFAULT_UPDATE_STATEMENT = "update " + TABLE_NAME + " set " + FIELDS_FOR_UPDATE.replaceAll(", ", "=?, ") + "=? where app_id = ?";
	private static final String DEFAULT_DELETE_STATEMENT = "delete from " + TABLE_NAME + " where app_id = ?";

	public int insertApplication(Application application) {
		int result = 0;
		try{
			result = jdbcTemplate.update(DEFAULT_INSERT_STATEMENT, getFields(application));
		} catch (DataAccessException ex){
			System.out.println(ex.toString());
		}
		return result;
	}

	public List<Application> registerApplications(List<Application> applicationList) {

		try{
			List<Object[]> batch = new ArrayList<Object[]>();
			for(Application application : applicationList) {
				batch.add(getFieldsForUpdate(application));
			}

			jdbcTemplate.batchUpdate(DEFAULT_UPDATE_STATEMENT, batch);

		} catch (DataAccessException ex) {
			Throwable rootCause = ex.getRootCause();
			if(rootCause instanceof BatchUpdateException) {
				BatchUpdateException bue = (BatchUpdateException)rootCause;
				int[] updateCount = bue.getUpdateCounts();
				for(int count : updateCount) {
					if(count == Statement.EXECUTE_FAILED) {
						log.error("Failed to register following application: " + applicationList.get(count).toLog());
						applicationList.remove(count);
					}
				}
			}
			log.error("Error occured while registering applications. Reason: " + ex.getMessage());
			applicationList = null;
		}
		return applicationList;
	}

	public int updateApplication(Application application) {
		return 1;
	}

	public int deleteApplication(String appId) {
		int result = 0;
		try {
			result = jdbcTemplate.update(DEFAULT_DELETE_STATEMENT, appId);
		} catch(DataAccessException ex) {
			System.out.println(ex.toString());
		}
		return result;
	}

	public Map<String, Object> registerApplication(Application application ) {
		List<SqlParameter> returnedResultSets = Arrays.<SqlParameter>asList(
				new SqlReturnResultSet("regResultCode", new ResultSetExtractor<Integer>() {

					@Override
					public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
						int registrationResult = 0;
						while(rs.next()) {
							registrationResult = rs.getInt("result");
						}
						return registrationResult;
					}
				}),
				new SqlReturnResultSet("managementIPList", new ResultSetExtractor<List<String>>() {

					@Override
					public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
						List<String> managementIPList = new ArrayList<String>();
						while(rs.next()) {
							managementIPList.add(rs.getString("management_IP"));
						}
						return managementIPList;
					}
				}));

		CallableStatementCreator csc = new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
				CallableStatement cs = con.prepareCall(REGISTER_APPLICATION_SP);
				cs.setInt(1, application.getAppId());
				cs.setInt(2, application.getCid() != 0 ? application.getCid() : 0);
				cs.setInt(3, application.getDid() != 0 ? application.getDid() : 0);
				cs.setInt(4, application.getAppImageId() == 0 ? 1 : application.getAppImageId());
				cs.setString(5, application.getCompliance() == null || application.getCompliance().equals("") ? "Default" : application.getCompliance());
				cs.setString(6, application.getAliasAppName());
				cs.setBoolean(7, application.getIsAdplEnabled());
				return cs;
			}};

			Map<String, Object> result = null;
			try{
				result = jdbcTemplate.call(csc, returnedResultSets);
			} catch (DataAccessException ex){
				System.out.println(ex.toString());
			}
			return result;
	}

	public Map<String, Object> callSlaCheckSP(Application application) {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName(SLA_CHECK_SP);
		SqlParameterSource in = new MapSqlParameterSource().addValues(getFieldsForSLA(application));
		Map<String, Object> out = jdbcCall.execute(in);
		return out;
	}
	
	public Map<String, Object> callApplicationLicenceFileGeneratorSP(String managementIP){
		Map<String, Object> out = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withProcedureName(APP_LIC_FILE_GEN_SP)
					.returningResultSet("applicationList", new ApplicationRowMapper());
			SqlParameterSource in = new MapSqlParameterSource().addValue("management_IP_in", managementIP);
			out = jdbcCall.execute(in);
		}catch(DataAccessException ex) {
			log.error("Exception occured while calling Application Licence File Generator SP. Reason: " + ex.getMessage());
		}
		return out;
	}

	private Object[] getFields(Application application) {
		return new Object[] {
				application.getAppName(),
				application.getAliasAppName() != null ? application.getAliasAppName() : null,
				application.getAppPath(),
				application.getMd5Checksum(),
				application.getSha2Checksum(),
				0, 0, 0, false, false, false, 1,
				application.getManagementIP(),
				new Timestamp(new Date().getTime()),
				new Timestamp(new Date().getTime())
		};
	}

	private Object[] getFieldsForUpdate(Application application) {
		return new Object[] {
				application.getAliasAppName() != null ? application.getAliasAppName() : null,
				application.getCid() != 0 ? application.getCid() : 0,
				application.getDid() != 0 ? application.getDid() : 0,
				application.getAdplAppId() != 0 ? application.getAdplAppId() : null,
				application.getIsAdplEnabled(),
				application.getIsMalware() == true ? application.getIsMalware() : false,
				application.getIsRegistered(),
				application.getAppImageId() != 0 ? application.getAppImageId() : 1,
				new Timestamp(new Date().getTime()),
				application.getAppId()
		};
	}

	private Map<String, Object> getFieldsForSLA(Application application) {

		Map<String, Object> slaInputMap = new HashMap<String, Object>();
		slaInputMap.put("app_name_var", application.getAppName());
		slaInputMap.put("app_path_var", application.getAppPath());
		slaInputMap.put("md5_checksum_var", application.getMd5Checksum());
		slaInputMap.put("sha2_checksum_var", application.getSha2Checksum());
		slaInputMap.put("cid_var", application.getCid() != 0 ? application.getCid() : 0);
		slaInputMap.put("did_var", application.getDid() != 0 ? application.getDid() : 0);
		slaInputMap.put("compliance_var", application.getCompliance() == null || application.getCompliance().equals("") ? "Default" : application.getCompliance());
		slaInputMap.put("management_IP_var", application.getManagementIP());
		return slaInputMap;
	}
	
	private static class ApplicationRowMapper implements RowMapper<LinkedHashMap<String, Object>> {

		@Override
		public LinkedHashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			LinkedHashMap<String, Object> application = new LinkedHashMap<String, Object>(6);
			application.put("appName", rs.getString("app_name"));
			application.put("appPath", rs.getString("app_path"));
			application.put("cid", rs.getInt("cid"));
			application.put("did", rs.getInt("did"));
			application.put("adplAppId", rs.getInt("adpl_app_id"));
			application.put("compliance", rs.getString("compliance"));
			return application;
		}
		
		
		
	}
}
