package com.cvs.avocado.models;

public class ApplicationWithInstances implements Comparable<ApplicationWithInstances>{

	private int appId;
	private String appName;
	private String appPath;
	private String aliasAppName;
	private String checkSum;
	private String sha256;
	private String compliance;
	private String custName;
	private String deptName;
	private int adplAppId;
	private String serverIP;
	private int port;
	private int protocol;
	private String managementIP;
	private boolean runningStatus;
	
	public ApplicationWithInstances() {
		
	}
	
	public ApplicationWithInstances(ApplicationWithInstances applicationWithInstances) {
		this.appId = applicationWithInstances.appId;
		this.appName = applicationWithInstances.appName;
		this.appPath = applicationWithInstances.appPath;
		this.aliasAppName = applicationWithInstances.aliasAppName;
		this.checkSum = applicationWithInstances.checkSum;
		this.sha256 = applicationWithInstances.sha256;
		this.compliance = applicationWithInstances.compliance;
		this.custName = applicationWithInstances.custName;
		this.deptName = applicationWithInstances.deptName;
		this.adplAppId = applicationWithInstances.adplAppId;
		this.serverIP = applicationWithInstances.serverIP;
		this.port = applicationWithInstances.port;
		this.protocol = applicationWithInstances.protocol;
		this.managementIP = applicationWithInstances.managementIP;
		this.runningStatus = applicationWithInstances.runningStatus;
	}

	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppPath() {
		return appPath;
	}
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
	public String getAliasAppName() {
		return aliasAppName;
	}
	public void setAliasAppName(String aliasAppName) {
		this.aliasAppName = aliasAppName;
	}
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	public String getSha256() {
		return sha256;
	}
	public void setSha256(String sha256) {
		this.sha256 = sha256;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getAdplAppId() {
		return adplAppId;
	}
	public void setAdplAppId(int adplAppId) {
		this.adplAppId = adplAppId;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getManagementIP() {
		return managementIP;
	}
	public void setManagementIP(String managementIP) {
		this.managementIP = managementIP;
	}
	public String getCompliance() {
		return compliance;
	}
	public void setCompliance(String compliance) {
		this.compliance = compliance;
	}
	public boolean getRunningStatus() {
		return runningStatus;
	}
	public void setRunningStatus(boolean runningStatus) {
		this.runningStatus = runningStatus;
	}

	@Override
	public int compareTo(ApplicationWithInstances o) {
		if(this.getAppId() == o.getAppId()
				&& this.getServerIP().equals(o.getServerIP())
				&& this.getPort() == o.getPort()
				&& this.getProtocol() == o.getProtocol()
				&& this.getManagementIP().equals(o.getManagementIP())) {
			return 0;
		}
		else return 1;
	}
	
	
}