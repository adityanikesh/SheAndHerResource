package com.cvs.avocado.models;

import java.util.ArrayList;
import java.util.List;

public class ApplicationWithInstancesAndStats extends ApplicationWithInstances {

	List<ServerStatsForApplicationInstance> serverStatsForApplicationInstanceList = null;
	
	public ApplicationWithInstancesAndStats(ApplicationWithInstances applicationWithInstances) {
		this.setAppId(applicationWithInstances.getAppId());
		this.setAppName(applicationWithInstances.getAppName());
		this.setAppPath(applicationWithInstances.getAppPath());
		this.setAliasAppName(applicationWithInstances.getAliasAppName());
		this.setCheckSum(applicationWithInstances.getCheckSum());
		this.setSha256(applicationWithInstances.getSha256());
		this.setCompliance(applicationWithInstances.getCompliance());
		this.setCustName(applicationWithInstances.getCustName());
		this.setDeptName(applicationWithInstances.getDeptName());
		this.setAdplAppId(applicationWithInstances.getAdplAppId());
		this.setServerIP(applicationWithInstances.getServerIP());
		this.setPort(applicationWithInstances.getPort());
		this.setProtocol(applicationWithInstances.getProtocol());
		this.setManagementIP(applicationWithInstances.getManagementIP());
		this.setRunningStatus(applicationWithInstances.getRunningStatus());
		serverStatsForApplicationInstanceList = new ArrayList<ServerStatsForApplicationInstance>();
	}

	public List<ServerStatsForApplicationInstance> getServerStatsForApplicationInstance() {
		return this.serverStatsForApplicationInstanceList;
	}

	public void addServerStatsForApplicationInstance(ServerStatsForApplicationInstance serverStatsForApplicationInstance) {
		this.serverStatsForApplicationInstanceList.add(serverStatsForApplicationInstance);
	}
	
	
}
