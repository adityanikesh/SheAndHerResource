package com.cvs.avocado.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.repositories.GlobalSettingsRepository;
import com.cvs.avocado.repositories.ServerInformationRepository;
import com.cvs.avocado.security.HttpServletFacade;
import com.cvs.avocado.utils.OrchestratorConstants;
import com.cvs.avocado.workers.SendGlobalSettingsWorker;

@RestController
public class GlobalSettingsController {
	
	@Autowired
	HttpServletFacade httpServlet;
	
	@Autowired
	GlobalSettingsRepository globalSettingsRepository;
	
	@Autowired
	ServerInformationRepository serverInformationRepository;
	
	@GetMapping("/adpl/globalSettings.ws")
	@PreAuthorize(value="hasAuthority('READ')")
	public Map<String, Object> getGlobalSettingsForManagementServer() {
		String managementIP = this.httpServlet.getRemoteAddress();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("mpIP", managementIP);
		data.put("discoverOn", this.globalSettingsRepository.getDiscoverOnlyMode());
		data.put("appInterval", this.globalSettingsRepository.getProcessInterval());
		data.put("orchUrl", this.globalSettingsRepository.getOrchestratorURL());
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("reqType", OrchestratorConstants.ZMQ_REQ_SEND_GLOBAL_SETTINGS);
		response.put("data", data);
		return response;
	}
	
	@PostMapping("/api/globalSettings/updatePollingInterval.ws")
	public boolean updatePollingInterval(int pollingInterval) {
		boolean result = false;
		result = this.globalSettingsRepository.updatePollinInterval(pollingInterval);
		SendGlobalSettingsWorker globalSettingsWorker = new SendGlobalSettingsWorker(globalSettingsRepository, serverInformationRepository);
		globalSettingsWorker.start();
		return result;
	}
	
}
