package com.cvs.avocado.models;

import java.sql.Timestamp;

public class ServerInformation {

	private String managementIP;
	private String serverIP;
	private boolean status;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	
	public ServerInformation(String managementIP, boolean status) {
		this.managementIP = managementIP;
		this.status = status;
	}
	
	public ServerInformation(String managementIP, String serverIP, boolean status) {
		this.managementIP = managementIP;
		this.serverIP = serverIP;
		this.status = status;
	}

	public String getManagementIP() {
		return managementIP;
	}

	public void setManagementIP(String managementIP) {
		this.managementIP = managementIP;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	
}
