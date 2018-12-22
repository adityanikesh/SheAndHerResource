package com.cvs.avocado.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.models.Application;
import com.cvs.avocado.models.SLAResponse;
import com.cvs.avocado.security.HttpServletFacade;
import com.cvs.avocado.services.ApplicationLicenceService;
import com.cvs.avocado.services.ApplicationService;
import com.cvs.avocado.services.GlobalSettingsService;
import com.cvs.avocado.services.ServerInformationService;
import com.cvs.avocado.utils.OrchestratorConstants;
import com.cvs.avocado.utils.OrchestratorMessageUtil;
import com.cvs.avocado.workers.SendAppLicenceWorker;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	ApplicationService applicationService;

	@Autowired
	HttpServletFacade httpServlet;

	@Autowired
	GlobalSettingsService globalSettingsService;

	@Autowired
	ApplicationLicenceService applLicService;
	
	@Autowired
	ServerInformationService serverInfoService;

	//	@PostMapping("/adpl/detected/application/create.ws")
	@PostMapping("/detect.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public SLAResponse detectApplication(Application application, @RequestParam(value = "pid") Integer pid) {
		application.setManagementIP(this.httpServlet.getRemoteAddress());
		Map<String, Object> out = this.applicationService.callSlaCheckSP(application);
		int slaResult = (Integer)out.get("slaResult");
//		this.checkForApplicationLicence((Integer)out.get("appId"), this.httpServlet.getRemoteAddress(), (boolean)out.get("isRegistered"), slaResult);
		SLAResponse slaResponse = new SLAResponse(slaResult, OrchestratorMessageUtil.getSLAMessage(slaResult));		
		slaResponse.setCid(application.getCid());
		slaResponse.setDid(application.getDid());
		slaResponse.setAdplAppId(application.getAdplAppId());
		slaResponse.setPid(pid);
		slaResponse.setDiscoverFlag(this.globalSettingsService.getDiscoverOnlyMode());
		return slaResponse;
	}

	@PostMapping("/register.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public Map<Integer, String> registerApplication(@RequestBody List<Application> applicationList) {
		Map<Integer, String> appListRegResult = new LinkedHashMap<Integer, String>();

		try {
			for(Application application: applicationList) {
				Map<String, Object> appRegResult = this.applicationService.registerApplication(application);
				int regResultCode = (Integer)appRegResult.get("regResultCode");
				appListRegResult.put(application.getAppId(), OrchestratorMessageUtil.getAppRegMessage(regResultCode));
				if(regResultCode != OrchestratorConstants.APP_REG_SUCCESS_REGISTRATION_CODE) {
					log.error("Failed to register following application: " + application.toLog() + "Reason: " + OrchestratorMessageUtil.getAppRegMessage(regResultCode));
				} else {
					log.info("Registration result of the following application: " + application.toLog() + "Result: " + OrchestratorMessageUtil.getAppRegMessage(regResultCode));
					this.sendAppLicence((List<String>)appRegResult.get("managementIPList"));
				}
			}
		} catch (Exception ex) {

		}
		return appListRegResult;
	}

	@PostMapping("/update.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public void updateApplication(@RequestBody Application application) {
		int result = this.applicationService.updateApplication(application);
		if(result > 0) {
			System.out.println("Application updated successfully");
		} else {
			System.out.println("Failed to update application");
		}
	}

	@DeleteMapping("/delete.ws")
	@PreAuthorize("hasAuthority('WRITE')")
	public void deleteApplication(String appId) {
		int result = this.applicationService.deleteApplication(appId);
		if(result > 0) {
			System.out.println("Application deleted successfully");
		}
	}

//	private void checkForApplicationLicence(int appId, String managementIP, boolean isRegistered, int slaResult) {
//		ApplicationLicence appLicence = this.applLicService.getLicenceByAppIdManagementIP(appId, managementIP);
//		if(appLicence == null) {
//			appLicence = new ApplicationLicence();
//			appLicence.setAppId(appId);
//			appLicence.setManagementIP(managementIP);
//			this.applLicService.insertOrUpdateApplicationLicence(appLicence);
//		} else {
//			if(slaResult == OrchestratorConstants.SLA_APP_NOT_SYNC_CODE
//					|| slaResult == OrchestratorConstants.SLA_NO_RECORD_IN_DB_CODE
//					|| slaResult == OrchestratorConstants.SLA_SHA2_MISMATCH_CODE
//					|| slaResult == OrchestratorConstants.SLA_MD5_MISMATCH_CODE) {
//				appLicence.setRegistered(isRegistered);
//				appLicence.setSent(false);
//				this.applLicService.insertOrUpdateApplicationLicence(appLicence);
//			}
//		}
//	}
	
	private void sendAppLicence(List<String> managementIPList) {
		for(String managementIP : managementIPList) {
			SendAppLicenceWorker sendAppLicWorker = new SendAppLicenceWorker(managementIP, applLicService, serverInfoService);
			sendAppLicWorker.start();
		}
	}
}
