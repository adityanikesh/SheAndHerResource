package com.cvs.avocado.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.avocado.models.Application;
import com.cvs.avocado.models.ApplicationLicence;
import com.cvs.avocado.repositories.ApplicationLicenceRepository;

@Service
public class ApplicationLicenceService {
	
	@Autowired
	ApplicationLicenceRepository applicationLicenceRepository;
	
	public ApplicationLicence getLicenceByAppIdManagementIP(int appId, String managementIP){
		return this.applicationLicenceRepository.getLicenceByAppIdManagementIP(appId, managementIP);
	}
	
//	public List<ApplicationLicence> getLicenceByManagementIP(String managementIP){
//		return this.applicationLicenceRepository.getLicenceByManagementIP(managementIP);
//	}

	public int insertOrUpdateApplicationLicence(ApplicationLicence appLicence) {
		return this.applicationLicenceRepository.insertOrUpdateApplicationLicence(appLicence);
	}
	
	public List<Application> updateApplicationLicence(List<Application> applicationList) {
		return this.applicationLicenceRepository.updateApplicationLicence(applicationList);
	}
	
	public boolean checkAppIdPresenceByManagementIP(String managementIP) {
		return this.applicationLicenceRepository.checkAppIdPresenceByManagementIP(managementIP);
	}
}
