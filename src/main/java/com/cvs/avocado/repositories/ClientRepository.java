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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.cvs.avocado.models.Client;

@Repository
public class ClientRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private static final String TABLE_NAME = "oauth_client_details";
	
	private static final String FIELDS_FOR_UPDATE = "client_id, client_secret, resource_ids, authorized_grant_types, role_id, enabled, access_token_validity, refresh_token_validity, create_time, update_time";
	private static final String FIELDS_FOR_INSERT = "client_secret " + FIELDS_FOR_UPDATE;
	private static final String FIELDS_FOR_SELECT = "o.client_id, o.resource_ids, r.role_name, o.authorized_grant_types, r.authorities, o.enabled, o.access_token_validity,o.refresh_token_validity, o.create_time, o.update_time";
	private static final String FIELDS_FOR_FIND = "o.client_secret, "+ FIELDS_FOR_SELECT;
	private static final String DEFAULT_SELECT_STATEMENT = "select " + FIELDS_FOR_SELECT + " from oauth_client_details o inner join roles r on o.role_id = r.role_id";
	private static final String DEFAULT_FIND_BY_ID_STATEMENT = "select " + FIELDS_FOR_FIND + " from oauth_client_details o inner join roles r on o.role_id = r.role_id where client_id=?";
	private static final String DEFAULT_INSERT_STATEMENT = "insert into "+ TABLE_NAME + "(" + FIELDS_FOR_INSERT + ") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public List<Client> getAllClients(){
		List<Client> clientList = null;
		try {
			clientList = new ArrayList<Client>(jdbcTemplate.query(DEFAULT_SELECT_STATEMENT, new ClientRowMapper()));
		} catch(DataAccessException ex) {
			System.out.println(ex.toString());
		}
		return clientList;
	}
	
	public int insertClient(Client client) throws DataAccessException{
		return  jdbcTemplate.update(DEFAULT_INSERT_STATEMENT, getFields(client));
	}
	
	public Client findClientByClientId(String clientId) {
		Client client = null;
		try{
			client = jdbcTemplate.queryForObject(DEFAULT_FIND_BY_ID_STATEMENT, new ClientSecretMapper(), clientId);
		}catch (IncorrectResultSizeDataAccessException ex){
			client = null;
		}
		return client;
	}
	
	private static class ClientRowMapper implements RowMapper<Client> {

		@Override
		public Client mapRow(ResultSet rs, int rowNum) throws SQLException {

			Client client = new Client(
					rs.getString("client_id"), 
					rs.getString("resource_ids"), 
					rs.getString("role_name"),
					rs.getString("authorized_grant_types"),
					rs.getString("authorities"),
					rs.getBoolean("enabled"),
					rs.getInt("access_token_validity"),
					rs.getInt("refresh_token_validity"));
			
			if(rs.getTimestamp("create_time") != null) {
				client.setCreateTime(rs.getTimestamp("create_time"));
			}
			
			if(rs.getTimestamp("update_time") != null) {
				client.setUpdateTime(rs.getTimestamp("update_time"));
			}
			return client;
		}
	}
	
	private static class ClientSecretMapper extends ClientRowMapper {
		
		ClientRowMapper clientRowMapper = new ClientRowMapper();
		public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
			Client client = clientRowMapper.mapRow(rs, rowNum);
			if(rs.getString("client_secret") != null) {
				client.setClientSecret(rs.getString("client_secret"));
			}
			return client;
		}
	}
	
	private Object[] getFields(Client client) {
		return new Object[]{
				passwordEncoder.encode("PRESET"),
				client.getClientId(),
				client.getResourceIds() != null ? StringUtils.collectionToCommaDelimitedString(client.getResourceIds()) : "appmanager",
				client.getAuthorizedGrantTypes() != null ? StringUtils.collectionToCommaDelimitedString(client.getAuthorizedGrantTypes()) : "client_credentials",
				3, 
				(client.isEnabled()) ? true : false,
				0, 0, new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())
		};
	}
}