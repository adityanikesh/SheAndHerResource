package com.cvs.avocado.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.models.ServerInformation;
import com.cvs.avocado.repositories.GlobalSettingsRepository;
import com.cvs.avocado.security.HttpServletFacade;
import com.cvs.avocado.services.ServerInformationService;
import com.cvs.avocado.utils.OrchestratorConstants;

@RestController
public class ServerInformationController {
	
	@Autowired
	ServerInformationService serverInformationService;
	
	@Autowired
	GlobalSettingsRepository globalSettingsRepository;
	
	@Autowired
	HttpServletFacade httpServlet;

	@PostMapping("/adpl/appmanager/beat.ws")
	@PreAuthorize(value="hasAuthority('WRITE')")
	public Map<String, Object> heartBeat() {
		String managementIP = this.httpServlet.getRemoteAddress();
		this.serverInformationService.insertOrUpdateServerInformation(new ServerInformation(managementIP, managementIP, true));
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
	
	@GetMapping("/api/status.ws")
	public boolean getServerStatus(@RequestParam ("managementIP") String managementIP) {
		return this.serverInformationService.getSeverStatus(managementIP);
	}
	
	
	
}
