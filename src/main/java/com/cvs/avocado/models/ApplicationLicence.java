package com.cvs.avocado.models;

import java.sql.Timestamp;
import java.util.Date;

public class ApplicationLicence {

	private int appId;
	private String managementIP;
	private boolean isSent;
	private boolean isRegistered;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	
	public int getAppId() {
		return appId;
	}
	
	public void setAppId(int appId) {
		this.appId = appId;
	}
	
	public String getManagementIP() {
		return managementIP;
	}
	
	public void setManagementIP(String managementIP) {
		this.managementIP = managementIP;
	}
	
	public boolean getSent() {
		return isSent;
	}
	
	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}
	public boolean getRegistered() {
		return isRegistered;
	}
	
	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	
	public Date getUpdatedOn() {
		return updatedOn;
	}
	
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	@Override
	public String toString() {
		return "ApplicationLicence [appId=" + appId + ", managementIP=" + managementIP + ", isSent=" + isSent
				+ ", isRegistered=" + isRegistered + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}
		
}
