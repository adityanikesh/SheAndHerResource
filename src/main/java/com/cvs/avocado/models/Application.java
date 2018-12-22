package com.cvs.avocado.models;

import java.sql.Timestamp;

public class Application {

	private int appId;
	private String appName;
	private String aliasAppName;
	private String appPath;
	private String md5Checksum;
	private String sha2Checksum;
	private int cid;
	private int did;
	private int adplAppId;
	private String compliance;
	private boolean isAdplEnabled;
	private boolean isMalware;
	private boolean isRegistered;
	private int appImageId;
	private String managementIP;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	
	public int getAppId() {
		return appId;
	}
	public void setAppId(int id) {
		this.appId = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAliasAppName() {
		return aliasAppName;
	}
	public void setAliasAppName(String aliasAppName) {
		this.aliasAppName = aliasAppName;
	}
	public String getAppPath() {
		return appPath;
	}
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
	public String getMd5Checksum() {
		return md5Checksum;
	}
	public void setMd5Checksum(String md5Checksum) {
		this.md5Checksum = md5Checksum;
	}
	public String getSha2Checksum() {
		return sha2Checksum;
	}
	public void setSha2Checksum(String sha2Checksum) {
		this.sha2Checksum = sha2Checksum;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public int getAdplAppId() {
		return adplAppId;
	}
	public void setAdplAppId(int adplAppId) {
		this.adplAppId = adplAppId;
	}
	public String getCompliance() {
		return compliance;
	}
	public void setCompliance(String compliance) {
		this.compliance = compliance;
	}
	public boolean getIsAdplEnabled() {
		return isAdplEnabled;
	}
	public void setIsAdplEnabled(boolean isAdplEnabled) {
		this.isAdplEnabled = isAdplEnabled;
	}
	public boolean getIsMalware() {
		return isMalware;
	}
	public void setIsMalware(boolean isMalware) {
		this.isMalware = isMalware;
	}
	public boolean getIsRegistered() {
		return isRegistered;
	}
	public void setIsRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
	public int getAppImageId() {
		return appImageId;
	}
	public void setAppImageId(int appImageId) {
		this.appImageId = appImageId;
	}
	public String getManagementIP() {
		return managementIP;
	}
	public void setManagementIP(String managementIP) {
		this.managementIP = managementIP;
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
	
	@Override
	public String toString() {
		return "Application [appId=" + appId + ", appName=" + appName + ", aliasAppName=" + aliasAppName + ", appPath="
				+ appPath + ", md5Checksum=" + md5Checksum + ", sha2Checksum=" + sha2Checksum + ", cid=" + cid
				+ ", did=" + did + ", adplAppId=" + adplAppId + ", compliance=" + compliance + ", isAdplEnabled="
				+ isAdplEnabled + ", isMalware=" + isMalware + ", isRegistered=" + isRegistered + ", appImageId="
				+ appImageId + ", managementIP=" + managementIP + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + "]";
	}
	
	public String toLog() {
		return "Application [appId=" + appId + ", appName=" + appName + ", aliasAppName=" + aliasAppName + ", appPath="
				+ appPath + ", md5Checksum=" + md5Checksum + ", sha2Checksum=" + sha2Checksum + ", cid=" + cid
				+ ", did=" + did + ", adplAppId=" + adplAppId + ", compliance=" + compliance + ", isMalware="
				+ isMalware + ", appImageId=" + appImageId + "]. ";
	}
	
	
	
	
}
