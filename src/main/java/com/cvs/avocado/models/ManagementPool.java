package com.cvs.avocado.models;

import java.sql.Timestamp;

public class ManagementPool {

	private int id;
	private String managementIPRangeStart;
	private String managementIPRangeEnd;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getManagementIPRangeStart() {
		return managementIPRangeStart;
	}
	
	public void setManagementIPRangeStart(String managementIPRangeStart) {
		this.managementIPRangeStart = managementIPRangeStart;
	}
	
	public String getManagementIPRangeEnd() {
		return managementIPRangeEnd;
	}
	
	public void setManagementIPRangeEnd(String managementIPRangeEnd) {
		this.managementIPRangeEnd = managementIPRangeEnd;
	}
	
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	
	public Timestamp getModifiedOn() {
		return modifiedOn;
	}
	
	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public String toString() {
		return "ManagementPool [id=" + id + ", managementIPRangeStart=" + managementIPRangeStart
				+ ", managementIPRangeEnd=" + managementIPRangeEnd + ", createdOn=" + createdOn + ", modifiedOn="
				+ modifiedOn + "]";
	}
	
}