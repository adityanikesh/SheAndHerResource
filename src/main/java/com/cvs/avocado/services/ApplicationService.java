package com.cvs.avocado.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.avocado.models.Application;
import com.cvs.avocado.repositories.ApplicationRepository;

@Service
public class ApplicationService {

	@Autowired
	ApplicationRepository applicationRepository;
	
	public Map<String, Object> callSlaCheckSP(Application application) {
		return this.applicationRepository.callSlaCheckSP(application);
	}
	
	public int insertApplication(Application application) {
		return this.applicationRepository.insertApplication(application);
	}
	
	public List<Application> registerApplication(List<Application> applicationList) {
		return this.applicationRepository.registerApplications(applicationList);
	}
	
	public int updateApplication(Application application) {
		return this.applicationRepository.updateApplication(application);
	}
	
	public int deleteApplication(String appId) {
		return this.applicationRepository.deleteApplication(appId);
	}
	
	public Map<String, Object> registerApplication(Application application ) {
		return this.applicationRepository.registerApplication(application);
	}
}
