package com.cvs.avocado.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.models.ManagementPool;
import com.cvs.avocado.services.ManagementPoolService;

@RestController
//@RequestMapping("/api/managementPool")
public class ManagementPoolController {
	
	@Autowired
	ManagementPoolService managementPoolService;
	
	@GetMapping("/api/managementPool/selectAll.ws")
	@PreAuthorize(value="hasAuthority('READ')")
	public List<ManagementPool> selectAllManagementPool() {
		return this.managementPoolService.selectAllManagementPool();
	}

	@PostMapping("/api/managementPool/insert.ws")
	@PreAuthorize(value="hasAuthority('WRITE')")
	public void insertManagementPool(ManagementPool managementPool) {
		int result = this.managementPoolService.insertManagementPool(managementPool);
		if(result == 1) {
			System.out.println("Management Pool inserted successfuly");
		}
	}
	
	@PostMapping("/api/managementPool/update.ws")
	@PreAuthorize(value="hasAuthority('WRITE')")
	public void updateManagementPool(ManagementPool managementPool) {
		int result = this.managementPoolService.updateManagementPool(managementPool);
		if(result == 1) {
			System.out.println("Management Pool updated successfuly");
		}
	}
	
	@DeleteMapping("/api/managementPool/delete.ws/{id}")
	@PreAuthorize(value="hasAuthority('WRITE')")
	public void deleteManagementPool(@PathVariable String id) {
		int result = this.managementPoolService.deleteManagementPool(id);
		if(result == 1) {
			System.out.println("Management Pool deleted successfuly");
		}
	}
}
