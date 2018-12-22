package com.cvs.avocado.workers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cvs.avocado.repositories.GlobalSettingsRepository;
import com.cvs.avocado.repositories.ServerInformationRepository;
import com.cvs.avocado.utils.JeroMqCalls;
import com.cvs.avocado.utils.OrchestratorConstants;

public class SendGlobalSettingsWorker extends Thread {

	ServerInformationRepository serverInfoRepository;
	GlobalSettingsRepository globalSettingsRepository;

	private static final Logger log = LogManager.getLogger();

	public SendGlobalSettingsWorker(GlobalSettingsRepository globalSettingsRepository, ServerInformationRepository serverInfoRepository) {
		this.serverInfoRepository = serverInfoRepository;
		this.globalSettingsRepository = globalSettingsRepository;
	}

	@Override
	public synchronized void run() {
		try {
			List<String> activeManagementServersList = this.serverInfoRepository.getActiveManagementServersList();
			if(activeManagementServersList != null && !activeManagementServersList.isEmpty()) {
				Map<String, Object> request = new HashMap<String, Object>();
				for(String managementIP : activeManagementServersList) {
					request.put("reqType", OrchestratorConstants.ZMQ_REQ_FETCH_GLOBAL_SETTINGS);
					request.put("data", "");
					new JeroMqCalls().sendMessage(managementIP, request);
					log.info("Fetch global settings notification sent to appmanager running on: "+managementIP);
				}
			}
		} catch (Exception e) {
			log.error("Exception occured while starting the thread to send global settings notification to appmanager. Reason: "+e.getMessage());
		}
	}
}
