package com.cvs.avocado.models;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"createTime", "updateTime"})
@SuppressWarnings("serial")
public class Client extends BaseClientDetails {

	private int roleId;
	private boolean enabled;
	private Timestamp createTime;
	private Timestamp updateTime;
	
	public Client(String clientId, String resourceIds, String scopes,
			String authorizedGrantTypes, String authorities, int roleId, boolean enabled, Integer accessTokenValiditySeconds,
			Integer refreshTokenValiditySeconds) {
		
		super(clientId, resourceIds, scopes, authorizedGrantTypes, authorities);
		setRoleId(roleId);
		setEnabled(enabled);
		setAccessTokenValiditySeconds(accessTokenValiditySeconds);
		setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
	}
	
	public Client(String clientId, String resourceIds, String scopes,
			String authorizedGrantTypes, String authorities, boolean enabled, Integer accessTokenValiditySeconds,
			Integer refreshTokenValiditySeconds) {
		
		super(clientId, resourceIds, scopes, authorizedGrantTypes, authorities);
		setEnabled(enabled);
		setAccessTokenValiditySeconds(accessTokenValiditySeconds);
		setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}