package com.cvs.avocado.models;

public class SLAResponse {

	private int cid;
	private int did;
	private int adplAppId;
	private int pid;
	private int slaCode;
	private String slaReason;
	private boolean discoverFlag;
	
	public SLAResponse(int slaCode, String slaReason) {
		super();
		this.slaCode = slaCode;
		this.slaReason = slaReason;
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getSlaCode() {
		return slaCode;
	}
	public void setSlaCode(int slaCode) {
		this.slaCode = slaCode;
	}
	public String getSlaReason() {
		return slaReason;
	}
	public void setSlaReason(String slaReason) {
		this.slaReason = slaReason;
	}
	public boolean isDiscoverFlag() {
		return discoverFlag;
	}
	public void setDiscoverFlag(boolean discoverFlag) {
		this.discoverFlag = discoverFlag;
	}
	@Override
	public String toString() {
		return "SLAResponse [cid=" + cid + ", did=" + did + ", adplAppId=" + adplAppId + ", pid=" + pid + ", slaCode="
				+ slaCode + ", slaReason=" + slaReason + ", discoverFlag=" + discoverFlag + "]";
	}
	
}
