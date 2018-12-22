package com.cvs.avocado.models;

import java.sql.Timestamp;

public class ServerStatsForApplicationInstance implements Comparable<ServerStatsForApplicationInstance>{

	private int pid;
	private String uuid;
	private String socket_uuid;
	private int client_count;
	private int sess_allowed;
	private int sess_rejected;
	private int sess_rej_policies;
	private int sess_rej_custid;
	private int sess_rej_depid;
	private int sess_rej_sla;
	private int sess_rej_osfails;
	private int close_reason;
	private Timestamp startedOn;
	private Timestamp lastSeenRunning;
		
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
	public int getClient_count() {
		return client_count;
	}
	public void setClient_count(int client_count) {
		this.client_count = client_count;
	}
	public int getSess_allowed() {
		return sess_allowed;
	}
	public void setSess_allowed(int sess_allowed) {
		this.sess_allowed = sess_allowed;
	}
	public int getSess_rejected() {
		return sess_rejected;
	}
	public void setSess_rejected(int sess_rejected) {
		this.sess_rejected = sess_rejected;
	}
	public int getSess_rej_policies() {
		return sess_rej_policies;
	}
	public void setSess_rej_policies(int sess_rej_policies) {
		this.sess_rej_policies = sess_rej_policies;
	}
	public int getSess_rej_custid() {
		return sess_rej_custid;
	}
	public void setSess_rej_custid(int sess_rej_custid) {
		this.sess_rej_custid = sess_rej_custid;
	}
	public int getSess_rej_depid() {
		return sess_rej_depid;
	}
	public void setSess_rej_depid(int sess_rej_depid) {
		this.sess_rej_depid = sess_rej_depid;
	}
	public int getSess_rej_sla() {
		return sess_rej_sla;
	}
	public void setSess_rej_sla(int sess_rej_sla) {
		this.sess_rej_sla = sess_rej_sla;
	}
	public int getSess_rej_osfails() {
		return sess_rej_osfails;
	}
	public void setSess_rej_osfails(int sess_rej_osfails) {
		this.sess_rej_osfails = sess_rej_osfails;
	}
	public int getClose_reason() {
		return close_reason;
	}
	public void setClose_reason(int close_reason) {
		this.close_reason = close_reason;
	}
	public Timestamp getStartedOn() {
		return startedOn;
	}
	public void setStartedOn(Timestamp startedOn) {
		this.startedOn = startedOn;
	}
	public Timestamp getLastSeenRunning() {
		return lastSeenRunning;
	}
	public void setLastSeenRunning(Timestamp lastSeenRunning) {
		this.lastSeenRunning = lastSeenRunning;
	}
	@Override
	public int compareTo(ServerStatsForApplicationInstance o) {
		if(this.uuid.equals(o.uuid)
				&& this.socket_uuid.equals(o.socket_uuid)) {
			return 0;
		}
		return 1;
	}
	
	
	
}
