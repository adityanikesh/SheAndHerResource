package com.cvs.avocado.models;

import java.util.ArrayList;
import java.util.List;

public class ServerStatsWithClientStatsForApplicationInstance extends ServerStatsForApplicationInstance {

	private List<ClientStatsForServerStats> listClientStatsForServerStats = null;
	
	public ServerStatsWithClientStatsForApplicationInstance(ServerStatsForApplicationInstance serverStatsForApplicationInstance){
		this.setPid(serverStatsForApplicationInstance.getPid());
		this.setUuid(serverStatsForApplicationInstance.getUuid());
		this.setSocket_uuid(serverStatsForApplicationInstance.getSocket_uuid());
		this.setClient_count(serverStatsForApplicationInstance.getClient_count());
		this.setSess_allowed(serverStatsForApplicationInstance.getSess_allowed());
		this.setSess_rejected(serverStatsForApplicationInstance.getSess_rejected());
		this.setSess_rej_policies(serverStatsForApplicationInstance.getSess_rej_policies());
		this.setSess_rej_custid(serverStatsForApplicationInstance.getSess_rej_custid());
		this.setSess_rej_depid(serverStatsForApplicationInstance.getSess_rej_depid());
		this.setSess_rej_sla(serverStatsForApplicationInstance.getSess_rej_sla());
		this.setSess_rej_osfails(serverStatsForApplicationInstance.getSess_rej_osfails());
		this.setClose_reason(serverStatsForApplicationInstance.getClose_reason());
		this.listClientStatsForServerStats = new ArrayList<ClientStatsForServerStats>();
	}

	public List<ClientStatsForServerStats> getListClientStatsForServerStats() {
		return listClientStatsForServerStats;
	}

	public void setListClientStatsForServerStats(List<ClientStatsForServerStats> listClientStatsForServerStats) {
		this.listClientStatsForServerStats = listClientStatsForServerStats;
	}

//	public void addClientStatsForServerStats(ClientStatsForServerStats clientStatsForServerStats) {
//		this.listClientStatsForServerStats.add(clientStatsForServerStats);
//	}
	
	
}
