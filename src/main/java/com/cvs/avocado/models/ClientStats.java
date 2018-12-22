package com.cvs.avocado.models;

public class ClientStats {

	private int client_port;
	private String client_ip;
	private String client_uuid;
	private String server_uuid;
	private String server_socket_uuid;
	private int send_count;
	private int recv_count;
	private long send_bytes;
	private long recv_bytes;
    private int pl_allowed;
	private int cust_dept_mismatch_count;
	private int sig_mismatch_count;
	private int pl_rej_policies;
    private int pl_rej_custid;
    private int pl_rej_depid;
    private int pl_rej_secsig;
    private int pl_rej_sqlinj;
    private int pl_bytes_rej_policies;
    private int pl_bytes_rej_custid;
    private int pl_bytes_rej_depid;
    private int pl_bytes_rej_secsig;
    private int pl_bytes_rej_sqlinj;
    private int close_reason;
    private long close_timestamp;
    
	public int getClient_port() {
		return client_port;
	}
	public void setClient_port(int client_port) {
		this.client_port = client_port;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	public String getClient_uuid() {
		return client_uuid;
	}
	public void setClient_uuid(String client_uuid) {
		this.client_uuid = client_uuid;
	}
	public String getServer_uuid() {
		return server_uuid;
	}
	public void setServer_uuid(String server_uuid) {
		this.server_uuid = server_uuid;
	}
	public String getServer_socket_uuid() {
		return server_socket_uuid;
	}
	public void setServer_socket_uuid(String server_socket_uuid) {
		this.server_socket_uuid = server_socket_uuid;
	}
	public int getSend_count() {
		return send_count;
	}
	public void setSend_count(int send_count) {
		this.send_count = send_count;
	}
	public int getRecv_count() {
		return recv_count;
	}
	public void setRecv_count(int recv_count) {
		this.recv_count = recv_count;
	}
	public long getSend_bytes() {
		return send_bytes;
	}
	public void setSend_bytes(long send_bytes) {
		this.send_bytes = send_bytes;
	}
	public long getRecv_bytes() {
		return recv_bytes;
	}
	public void setRecv_bytes(long recv_bytes) {
		this.recv_bytes = recv_bytes;
	}
	public int getPl_allowed() {
		return pl_allowed;
	}
	public void setPl_allowed(int pl_allowed) {
		this.pl_allowed = pl_allowed;
	}
	public int getCust_dept_mismatch_count() {
		return cust_dept_mismatch_count;
	}
	public void setCust_dept_mismatch_count(int cust_dept_mismatch_count) {
		this.cust_dept_mismatch_count = cust_dept_mismatch_count;
	}
	public int getSig_mismatch_count() {
		return sig_mismatch_count;
	}
	public void setSig_mismatch_count(int sig_mismatch_count) {
		this.sig_mismatch_count = sig_mismatch_count;
	}
	public int getPl_rej_policies() {
		return pl_rej_policies;
	}
	public void setPl_rej_policies(int pl_rej_policies) {
		this.pl_rej_policies = pl_rej_policies;
	}
	public int getPl_rej_custid() {
		return pl_rej_custid;
	}
	public void setPl_rej_custid(int pl_rej_custid) {
		this.pl_rej_custid = pl_rej_custid;
	}
	public int getPl_rej_depid() {
		return pl_rej_depid;
	}
	public void setPl_rej_depid(int pl_rej_depid) {
		this.pl_rej_depid = pl_rej_depid;
	}
	public int getPl_rej_secsig() {
		return pl_rej_secsig;
	}
	public void setPl_rej_secsig(int pl_rej_secsig) {
		this.pl_rej_secsig = pl_rej_secsig;
	}
	public int getPl_rej_sqlinj() {
		return pl_rej_sqlinj;
	}
	public void setPl_rej_sqlinj(int pl_rej_sqlinj) {
		this.pl_rej_sqlinj = pl_rej_sqlinj;
	}
	public int getPl_bytes_rej_policies() {
		return pl_bytes_rej_policies;
	}
	public void setPl_bytes_rej_policies(int pl_bytes_rej_policies) {
		this.pl_bytes_rej_policies = pl_bytes_rej_policies;
	}
	public int getPl_bytes_rej_custid() {
		return pl_bytes_rej_custid;
	}
	public void setPl_bytes_rej_custid(int pl_bytes_rej_custid) {
		this.pl_bytes_rej_custid = pl_bytes_rej_custid;
	}
	public int getPl_bytes_rej_depid() {
		return pl_bytes_rej_depid;
	}
	public void setPl_bytes_rej_depid(int pl_bytes_rej_depid) {
		this.pl_bytes_rej_depid = pl_bytes_rej_depid;
	}
	public int getPl_bytes_rej_secsig() {
		return pl_bytes_rej_secsig;
	}
	public void setPl_bytes_rej_secsig(int pl_bytes_rej_secsig) {
		this.pl_bytes_rej_secsig = pl_bytes_rej_secsig;
	}
	public int getPl_bytes_rej_sqlinj() {
		return pl_bytes_rej_sqlinj;
	}
	public void setPl_bytes_rej_sqlinj(int pl_bytes_rej_sqlinj) {
		this.pl_bytes_rej_sqlinj = pl_bytes_rej_sqlinj;
	}
	public int getClose_reason() {
		return close_reason;
	}
	public void setClose_reason(int close_reason) {
		this.close_reason = close_reason;
	}
	public long getClose_timestamp() {
		return close_timestamp;
	}
	public void setClose_timestamp(long close_timestamp) {
		this.close_timestamp = close_timestamp;
	}
	
}
