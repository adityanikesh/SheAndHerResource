package com.cvs.avocado.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.avocado.models.ManagementPool;
import com.cvs.avocado.repositories.ManagementPoolRepository;

@Service
public class ManagementPoolService {

	@Autowired
	ManagementPoolRepository managementPoolRepository;
	
	public List<ManagementPool> selectAllManagementPool() {
		return this.managementPoolRepository.selectAllManagementPool();
	}

	public int insertManagementPool(ManagementPool managementPool) {
		return this.managementPoolRepository.insertManagementPool(managementPool);
	}
	
	public int updateManagementPool(ManagementPool managementPool) {
		return this.managementPoolRepository.updateManagementPool(managementPool);
	}
	
	public int deleteManagementPool(String id) {
		return this.managementPoolRepository.deleteManagementPool(id);
	}
	
	public int findManagementIP(String managementIP) {
		return this.managementPoolRepository.findManagementIP(managementIP);
	}
}