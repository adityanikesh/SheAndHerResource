package com.cvs.avocado.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.cvs.avocado.models.ApplicationWithInstances;
import com.cvs.avocado.models.ApplicationWithInstancesAndStats;
import com.cvs.avocado.models.ApplicationWithInstancesAndStatsAndClientStats;
import com.cvs.avocado.models.ClientStatsForServerStats;
import com.cvs.avocado.models.ServerStatsForApplicationInstance;
import com.cvs.avocado.models.ServerStatsWithClientStatsForApplicationInstance;

@Repository
public class DataRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String APPLICATION_WITH_INSTANCES_FIELDS = "app_id, app_name, app_path, aliasAppName, md_checksum, sha2, compliance, cust_name, dept_name, adpl_app_id, server_ip, server_port, protocol, management_ip, running_status";
	private static final String APPLICATION_WITH_INSTANCES_AND_STATS_FIELDS = APPLICATION_WITH_INSTANCES_FIELDS + ", uuid, socket_uuid, pid, client_count, sess_allowed, sess_rejected, sess_rej_policies, sess_rej_custid, sess_rej_depid, sess_rej_sla, sess_rej_osfails, start_time, last_seen_running";
	private static final String APPLICATION_WITH_INSTANCES_AND_STATS_AND_CLIENT_STATS_FIELDS = APPLICATION_WITH_INSTANCES_AND_STATS_FIELDS + ", client_ip, client_port, client_uuid, send_count, recv_count, send_bytes, recv_bytes, cust_dept_mismatch_count, sig_mismatch_count, pl_allowed, pl_rej_policies, pl_rej_custid, pl_rej_depid, pl_rej_secsig, pl_rej_sqlinj, pl_bytes_rej_policies, pl_bytes_rej_custid, pl_bytes_rej_depid, pl_bytes_rej_secsig, pl_bytes_rej_sqlinj, close_reason, close_timestamp";
	private static final String APPLICATION_WITH_INSTANCES_VIEW = "V_APPLICATION_WITH_INSTANCES_INFO";
	private static final String APPLICATION_WITH_INSTANCES_AND_STATS_VIEW = "V_APPLICATION_WITH_INSTANCES_AND_STATS_INFO";
	private static final String APPLICATION_WITH_INSTANCES_AND_STATS_AND_CLIENT_STATS_VIEW = "V_APPLICATION_WITH_INSTANCES_AND_STATS_AND_CLIENT_STATS_INFO";
	private static final String GET_APPLICATION_WITH_INSTANCES_STATEMENT = "select " + APPLICATION_WITH_INSTANCES_FIELDS + " from " + APPLICATION_WITH_INSTANCES_VIEW;
	private static final String GET_APPLICATION_WITH_INSTANCES_AND_STATS_STATEMENT = "select " + APPLICATION_WITH_INSTANCES_AND_STATS_FIELDS + " from " + APPLICATION_WITH_INSTANCES_AND_STATS_VIEW;
	private static final String GET_APPLICATION_WITH_INSTANCES_AND_STATS_AND_CLIENT_STATS_STATEMENT = "select " + APPLICATION_WITH_INSTANCES_AND_STATS_AND_CLIENT_STATS_FIELDS + " from " + APPLICATION_WITH_INSTANCES_AND_STATS_AND_CLIENT_STATS_VIEW;

	public List<ApplicationWithInstances> getApplicationWithInstances() {
		List<ApplicationWithInstances> applicationWithInstancesList = null;
		try {
			applicationWithInstancesList = new ArrayList<ApplicationWithInstances>(jdbcTemplate.query(GET_APPLICATION_WITH_INSTANCES_STATEMENT, new ApplicationWithInstancesResultSetExtractor()));
		} catch(DataAccessException ex) {
			System.out.println(ex.toString());
		}
		return applicationWithInstancesList;
	}

	public List<ApplicationWithInstancesAndStats> getApplicationWithInstancesAndStats() {
		List<ApplicationWithInstancesAndStats> applicationWithInstancesAndStatsList = null;
		try {
			applicationWithInstancesAndStatsList = jdbcTemplate.query(GET_APPLICATION_WITH_INSTANCES_AND_STATS_STATEMENT, new ApplicationWithInstancesAndStatsResultSetExtractor());
		} catch(DataAccessException ex) {
			System.out.println(ex.toString());
		}
		return applicationWithInstancesAndStatsList;
	}

	public List<ApplicationWithInstancesAndStatsAndClientStats> getApplicationWithInstancesAndStatsAndClientStats() {
		List<ApplicationWithInstancesAndStatsAndClientStats> listApplicationWithInstancesAndStatsAndClientStats = null;
		try {
			listApplicationWithInstancesAndStatsAndClientStats = jdbcTemplate.query(GET_APPLICATION_WITH_INSTANCES_AND_STATS_AND_CLIENT_STATS_STATEMENT, new ApplicationWithInstancesAndStatsAndClientStatsResultSetExtractor());
		} catch(DataAccessException ex) {
			System.out.println(ex.toString());
		}
		return listApplicationWithInstancesAndStatsAndClientStats;
	}

	private static class ApplicationWithInstancesResultSetExtractor implements ResultSetExtractor<List<ApplicationWithInstances>>{

		List<ApplicationWithInstances> listApplicationWithInstances = new ArrayList<ApplicationWithInstances>();

		@Override
		public List<ApplicationWithInstances> extractData(ResultSet rs) throws SQLException, DataAccessException {
			while(rs.next()) {
				ApplicationWithInstances applicationWithInstances = mapApplicationInstancesRows(rs);
				listApplicationWithInstances.add(applicationWithInstances);
			}
			return listApplicationWithInstances;
		}

		public ApplicationWithInstances mapApplicationInstancesRows(ResultSet rs) throws SQLException {
			ApplicationWithInstances applicationWithInstances = new ApplicationWithInstances();
			applicationWithInstances.setAppId(rs.getInt("app_id"));
			applicationWithInstances.setAppName(rs.getString("app_name"));
			applicationWithInstances.setAppPath(rs.getString("app_path"));
			applicationWithInstances.setAliasAppName(rs.getString("aliasAppName"));
			applicationWithInstances.setCheckSum(rs.getString("md_checksum"));
			applicationWithInstances.setSha256(rs.getString("sha2"));
			applicationWithInstances.setCompliance(rs.getString("compliance"));
			applicationWithInstances.setCustName(rs.getString("cust_name"));
			applicationWithInstances.setDeptName(rs.getString("dept_name"));
			applicationWithInstances.setAdplAppId(rs.getInt("adpl_app_id"));
			applicationWithInstances.setServerIP(rs.getString("server_ip"));
			applicationWithInstances.setPort(rs.getInt("server_port"));
			applicationWithInstances.setProtocol(rs.getInt("protocol"));
			applicationWithInstances.setManagementIP(rs.getString("management_ip"));
			applicationWithInstances.setRunningStatus(rs.getBoolean("running_status"));
			return applicationWithInstances;
		}

	}

	public static class ApplicationWithInstancesAndStatsResultSetExtractor implements ResultSetExtractor<List<ApplicationWithInstancesAndStats>> {

		List<ApplicationWithInstancesAndStats> listApplicationWithInstancesAndStats = new ArrayList<ApplicationWithInstancesAndStats>();
		ApplicationWithInstances lastApplicationInstance = null;
		ApplicationWithInstancesAndStats applicationWithInstancesAndStats = null;
		ApplicationWithInstancesResultSetExtractor applicationWithInstancesResultSetExtractor = new ApplicationWithInstancesResultSetExtractor();

		@Override
		public List<ApplicationWithInstancesAndStats> extractData(ResultSet rs) throws SQLException, DataAccessException {

			while(rs.next()) {
				ApplicationWithInstances currentApplicationWithInstances = applicationWithInstancesResultSetExtractor.mapApplicationInstancesRows(rs);
				if(lastApplicationInstance == null) {
					applicationWithInstancesAndStats = new ApplicationWithInstancesAndStats(currentApplicationWithInstances);
					applicationWithInstancesAndStats.addServerStatsForApplicationInstance(mapServerStatsRows(rs));
					lastApplicationInstance = currentApplicationWithInstances;
				} else {
					if(currentApplicationWithInstances.compareTo(lastApplicationInstance) == 0) {
						applicationWithInstancesAndStats.addServerStatsForApplicationInstance(mapServerStatsRows(rs));
					} else {
						listApplicationWithInstancesAndStats.add(applicationWithInstancesAndStats);
						applicationWithInstancesAndStats = new ApplicationWithInstancesAndStats(currentApplicationWithInstances);
						applicationWithInstancesAndStats.addServerStatsForApplicationInstance(mapServerStatsRows(rs));
						lastApplicationInstance = currentApplicationWithInstances;
					}
				}
			}
			listApplicationWithInstancesAndStats.add(applicationWithInstancesAndStats);
			return listApplicationWithInstancesAndStats;
		}

		public ServerStatsForApplicationInstance mapServerStatsRows(ResultSet rs) throws SQLException {
			ServerStatsForApplicationInstance serverStats = new ServerStatsForApplicationInstance();
			serverStats.setUuid(rs.getString("uuid"));
			serverStats.setSocket_uuid(rs.getString("socket_uuid"));
			serverStats.setPid(rs.getInt("pid"));
			serverStats.setClient_count(rs.getInt("client_count"));
			serverStats.setSess_allowed(rs.getInt("sess_allowed"));
			serverStats.setSess_rejected(rs.getInt("sess_rejected"));
			serverStats.setSess_rej_policies(rs.getInt("sess_rej_policies"));
			serverStats.setSess_rej_custid(rs.getInt("sess_rej_custid"));
			serverStats.setSess_rej_depid(rs.getInt("sess_rej_depid"));
			serverStats.setSess_rej_sla(rs.getInt("sess_rej_sla"));
			serverStats.setSess_rej_osfails(rs.getInt("sess_rej_osfails"));
			//			serverStats.setClose_reason(rs.getInt("close_reason"));
			serverStats.setStartedOn(rs.getTimestamp("start_time"));
			serverStats.setLastSeenRunning(rs.getTimestamp("last_seen_running"));
			return serverStats;
		}

	}

	public static class ApplicationWithInstancesAndStatsAndClientStatsResultSetExtractor implements ResultSetExtractor<List<ApplicationWithInstancesAndStatsAndClientStats>> {

		List<ApplicationWithInstancesAndStatsAndClientStats> listApplicationWithInstancesAndStatsAndClientStats = new ArrayList<ApplicationWithInstancesAndStatsAndClientStats>();
		List<ClientStatsForServerStats> listClientStatsForServerStats = null; 
		ApplicationWithInstances lastApplicationInstance = null;
		ServerStatsForApplicationInstance lastServerStatsForApplicationInstance = null;
		ServerStatsWithClientStatsForApplicationInstance serverStatsWithClientStatsForApplicationInstance = null;
		ApplicationWithInstancesAndStatsAndClientStats applicationWithInstancesAndStatsAndClientStats = null;
		ApplicationWithInstancesResultSetExtractor applicationWithInstancesResultSetExtractor = new ApplicationWithInstancesResultSetExtractor();
		ApplicationWithInstancesAndStatsResultSetExtractor applicationWithInstancesAndStatsResultSetExtractor = new ApplicationWithInstancesAndStatsResultSetExtractor();

		@Override
		public List<ApplicationWithInstancesAndStatsAndClientStats> extractData(ResultSet rs)
				throws SQLException, DataAccessException {

			while(rs.next()) {

				ClientStatsForServerStats clientStatsForServerStats = mapClientForServerStats(rs);
				ServerStatsForApplicationInstance currentServerStatsForApplicationInstance = applicationWithInstancesAndStatsResultSetExtractor.mapServerStatsRows(rs);

				if(lastServerStatsForApplicationInstance == null) {
					listClientStatsForServerStats = new ArrayList<ClientStatsForServerStats>();
					
					if(clientStatsForServerStats.getClient_port() != 0
							&& (clientStatsForServerStats.getClient_ip() != null || !clientStatsForServerStats.getClient_ip().equals(""))
							&& (clientStatsForServerStats.getClient_uuid() != null || clientStatsForServerStats.getClient_uuid().equals(""))) {
						listClientStatsForServerStats.add(clientStatsForServerStats);
					}
					lastServerStatsForApplicationInstance = currentServerStatsForApplicationInstance;
				} else {

					if(lastServerStatsForApplicationInstance.compareTo(currentServerStatsForApplicationInstance) == 0) {
						if(clientStatsForServerStats.getClient_port() != 0
								&& (clientStatsForServerStats.getClient_ip() != null || !clientStatsForServerStats.getClient_ip().equals(""))
								&& (clientStatsForServerStats.getClient_uuid() != null || clientStatsForServerStats.getClient_uuid().equals(""))) {
							listClientStatsForServerStats.add(clientStatsForServerStats);
						}
					} else {	
						serverStatsWithClientStatsForApplicationInstance = new ServerStatsWithClientStatsForApplicationInstance(lastServerStatsForApplicationInstance);
						serverStatsWithClientStatsForApplicationInstance.setListClientStatsForServerStats(listClientStatsForServerStats);
						listClientStatsForServerStats = new ArrayList<ClientStatsForServerStats>();
						
						if(clientStatsForServerStats.getClient_port() != 0
								&& (clientStatsForServerStats.getClient_ip() != null || !clientStatsForServerStats.getClient_ip().equals(""))
								&& (clientStatsForServerStats.getClient_uuid() != null || clientStatsForServerStats.getClient_uuid().equals(""))) {
							listClientStatsForServerStats.add(clientStatsForServerStats);
						}
						lastServerStatsForApplicationInstance = currentServerStatsForApplicationInstance;		
						ApplicationWithInstances currentApplicationWithInstances = applicationWithInstancesResultSetExtractor.mapApplicationInstancesRows(rs);

						if(lastApplicationInstance == null) {
							applicationWithInstancesAndStatsAndClientStats = new ApplicationWithInstancesAndStatsAndClientStats(currentApplicationWithInstances);
							applicationWithInstancesAndStatsAndClientStats.addServerStatsWithClientStatsForApplicationInstance(serverStatsWithClientStatsForApplicationInstance);
							lastApplicationInstance = currentApplicationWithInstances;
						} else {

							if(lastApplicationInstance.compareTo(currentApplicationWithInstances) == 0) {
								applicationWithInstancesAndStatsAndClientStats.addServerStatsWithClientStatsForApplicationInstance(serverStatsWithClientStatsForApplicationInstance);
							} else {
								applicationWithInstancesAndStatsAndClientStats.addServerStatsWithClientStatsForApplicationInstance(serverStatsWithClientStatsForApplicationInstance);
								listApplicationWithInstancesAndStatsAndClientStats.add(applicationWithInstancesAndStatsAndClientStats);
								applicationWithInstancesAndStatsAndClientStats = new ApplicationWithInstancesAndStatsAndClientStats(currentApplicationWithInstances);
								lastApplicationInstance = currentApplicationWithInstances;
							}
						}
					}
				}
			}
			serverStatsWithClientStatsForApplicationInstance = new ServerStatsWithClientStatsForApplicationInstance(lastServerStatsForApplicationInstance);
			serverStatsWithClientStatsForApplicationInstance.setListClientStatsForServerStats(listClientStatsForServerStats);
			applicationWithInstancesAndStatsAndClientStats.addServerStatsWithClientStatsForApplicationInstance(serverStatsWithClientStatsForApplicationInstance);
			listApplicationWithInstancesAndStatsAndClientStats.add(applicationWithInstancesAndStatsAndClientStats);
			return listApplicationWithInstancesAndStatsAndClientStats;
		}

		public ClientStatsForServerStats mapClientForServerStats(ResultSet rs) throws SQLException {
			ClientStatsForServerStats clientStatsForServerStats = new ClientStatsForServerStats();
			clientStatsForServerStats.setClient_ip(rs.getString("client_ip"));
			clientStatsForServerStats.setClient_port(rs.getInt("client_port"));
			clientStatsForServerStats.setClient_uuid(rs.getString("client_uuid"));
			clientStatsForServerStats.setSend_count(rs.getInt("send_count"));
			clientStatsForServerStats.setRecv_count(rs.getInt("recv_count"));
			clientStatsForServerStats.setSend_bytes(rs.getInt("send_bytes"));
			clientStatsForServerStats.setRecv_bytes(rs.getInt("recv_bytes"));
			clientStatsForServerStats.setCust_dept_mismatch_count(rs.getInt("cust_dept_mismatch_count"));
			clientStatsForServerStats.setSig_mismatch_count(rs.getInt("sig_mismatch_count"));
			clientStatsForServerStats.setPl_allowed(rs.getInt("pl_allowed"));
			clientStatsForServerStats.setPl_rej_policies(rs.getInt("pl_rej_policies"));
			clientStatsForServerStats.setPl_rej_custid(rs.getInt("pl_rej_custid"));
			clientStatsForServerStats.setPl_rej_depid(rs.getInt("pl_rej_depid"));
			clientStatsForServerStats.setPl_rej_secsig(rs.getInt("pl_rej_secsig"));
			clientStatsForServerStats.setPl_rej_sqlinj(rs.getInt("pl_rej_sqlinj"));
			clientStatsForServerStats.setPl_bytes_rej_policies(rs.getInt("pl_bytes_rej_policies"));
			clientStatsForServerStats.setPl_bytes_rej_custid(rs.getInt("pl_bytes_rej_custid"));
			clientStatsForServerStats.setPl_bytes_rej_depid(rs.getInt("pl_bytes_rej_depid"));
			clientStatsForServerStats.setPl_bytes_rej_secsig(rs.getInt("pl_bytes_rej_secsig"));
			clientStatsForServerStats.setPl_bytes_rej_sqlinj(rs.getInt("pl_bytes_rej_sqlinj"));
			clientStatsForServerStats.setClose_reason(rs.getInt("close_reason"));
			clientStatsForServerStats.setClose_timestamp(rs.getTimestamp("close_timestamp"));

			return clientStatsForServerStats;
		}

	}
}
