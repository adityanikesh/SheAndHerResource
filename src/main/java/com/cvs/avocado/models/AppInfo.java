package com.cvs.avocado.models;

public class AppInfo {

	private int id;
	private int appId;
	private int protocol;
	private int port;
	private String serverIP;
	private String managementIP;
	private int pid;
	private String uuid;
	private String socket_uuid;
	private boolean isDeleted;
	private int is_child;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
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
	public String getManagementIP() {
		return managementIP;
	}
	public void setManagementIP(String managementIP) {
		this.managementIP = managementIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSocket_uuid() {
		return socket_uuid;
	}
	public void setSocket_uuid(String socket_uuid) {
		this.socket_uuid = socket_uuid;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getIs_child() {
		return is_child;
	}
	public void setIs_child(int is_child) {
		this.is_child = is_child;
	}
		
}
