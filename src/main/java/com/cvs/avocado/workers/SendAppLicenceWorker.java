package com.cvs.avocado.workers;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cvs.avocado.services.ApplicationLicenceService;
import com.cvs.avocado.services.ServerInformationService;
import com.cvs.avocado.utils.JeroMqCalls;
import com.cvs.avocado.utils.OrchestratorConstants;

public class SendAppLicenceWorker extends Thread {

	String managementIP;
	ApplicationLicenceService appLicService;
	ServerInformationService serverInfoService;

	private static final Logger log = LogManager.getLogger();

	public SendAppLicenceWorker(String managementIP, ApplicationLicenceService appLicService, ServerInformationService serverInfoService) {
		this.managementIP = managementIP;
		this.appLicService = appLicService;
		this.serverInfoService = serverInfoService;
	}

	@Override
	public synchronized void run() {
		try {
			boolean status = this.serverInfoService.getSeverStatus(managementIP);
			if(status) {
				boolean isPresent = this.appLicService.checkAppIdPresenceByManagementIP(managementIP);
				Map<String, Object> request = new HashMap<String, Object>();
				if(isPresent) {
					request.put("reqType", OrchestratorConstants.ZMQ_REQ_FETCH_LICENCE);
					request.put("data", "");
					new JeroMqCalls().sendMessage(managementIP, request);
					log.info("Fetch licence file notification sent to appmanager running on: "+managementIP);
				} else {
					request.put("reqType", OrchestratorConstants.ZMQ_REQ_DELETE_LICENCE);
					request.put("data", "");
					new JeroMqCalls().sendMessage(managementIP, request);
					log.info("Delete licence file notification sent to appmanager running on: "+managementIP);
				}
			}
		} catch (Exception e) {
			log.error("Exception occured while starting the thread to send application license notification to appmanager running on: " + managementIP + ". Reason: "+e.getMessage());
		}
	}
}
