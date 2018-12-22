package com.cvs.avocado.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.models.ApplicationWithInstances;
import com.cvs.avocado.models.ApplicationWithInstancesAndStats;
import com.cvs.avocado.models.ApplicationWithInstancesAndStatsAndClientStats;
import com.cvs.avocado.repositories.DataRepository;

@RestController
@RequestMapping("/adpl")
public class DataController {
	
	@Autowired
	DataRepository dataRepository;
	
	@GetMapping("/getApplicationWithInstances.ws")
	public List<ApplicationWithInstances> getApplicationWithInstances() {
		return this.dataRepository.getApplicationWithInstances();
	}
	
	@GetMapping("/getApplicationWithInstancesAndStats.ws")
	public List<ApplicationWithInstancesAndStats> getApplicationWithInstancesAndStats() {
		return this.dataRepository.getApplicationWithInstancesAndStats();
	}
	
	@GetMapping("/getApplicationWithInstancesAndStatsAndClientStats.ws")
	public List<ApplicationWithInstancesAndStatsAndClientStats> getApplicationWithInstancesAndStatsAndClientStats() {
		return this.dataRepository.getApplicationWithInstancesAndStatsAndClientStats();
	}
}
