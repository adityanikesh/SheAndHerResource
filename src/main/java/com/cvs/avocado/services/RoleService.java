package com.cvs.avocado.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cvs.avocado.models.Role;
import com.cvs.avocado.repositories.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public List<Role> findAllRoles() {
		return this.roleRepository.findAll();
	}
	
	public Optional<Role> findRoleById(long roleId) {
		return this.roleRepository.findById(roleId);
	}

}
