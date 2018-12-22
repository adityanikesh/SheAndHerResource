package com.cvs.avocado.models;

import java.util.ArrayList;
import java.util.List;

public class ApplicationWithInstancesAndStatsAndClientStats extends ApplicationWithInstances{

	List<ServerStatsWithClientStatsForApplicationInstance> listServerStatsWithClientStatsForApplicationInstance = null;
	
	public ApplicationWithInstancesAndStatsAndClientStats(ApplicationWithInstances applicationWithInstances) {
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
		listServerStatsWithClientStatsForApplicationInstance = new ArrayList<ServerStatsWithClientStatsForApplicationInstance>();
	}

	public List<ServerStatsWithClientStatsForApplicationInstance> getListServerStatsWithClientStatsForApplicationInstance() {
		return listServerStatsWithClientStatsForApplicationInstance;
	}

	public void addServerStatsWithClientStatsForApplicationInstance(ServerStatsWithClientStatsForApplicationInstance serverStatsWithClientStatsForApplicationInstance) {
		this.listServerStatsWithClientStatsForApplicationInstance.add(serverStatsWithClientStatsForApplicationInstance);
	}
	
}
