package com.cvs.avocado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.avocado.models.ServerInformation;
import com.cvs.avocado.repositories.ServerInformationRepository;

@Service
public class ServerInformationService {

	@Autowired
	ServerInformationRepository serverInformationRepository;
	
	public boolean getSeverStatus(String managementIP) {
		return this.serverInformationRepository.getServerStatus(managementIP);
	}
	
	public int insertOrUpdateServerInformation(ServerInformation serverInformation) {
		return this.serverInformationRepository.insertOrUpdateServerInformation(serverInformation);
	}
	
	public int deleteServerInformation(String managementIP) {
		return this.serverInformationRepository.deleteServerStatusInformation(managementIP);
	}
}
