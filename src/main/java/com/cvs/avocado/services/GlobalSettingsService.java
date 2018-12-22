package com.cvs.avocado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.avocado.repositories.GlobalSettingsRepository;

@Service
public class GlobalSettingsService {

	@Autowired
	GlobalSettingsRepository globalSettingsRepository;
	
	public boolean getDiscoverOnlyMode() {
		return this.globalSettingsRepository.getDiscoverOnlyMode();
	}
}
