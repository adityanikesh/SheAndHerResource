package com.cvs.avocado.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvs.avocado.models.Role;
import com.cvs.avocado.models.User;
import com.cvs.avocado.services.RoleService;
import com.cvs.avocado.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/getAllRoles")
	@PreAuthorize("permitAll()")
	public List<Role> getAllRoles() {
		return this.roleService.findAllRoles();
	}
	
	@PreAuthorize("hasAuthority('READ')")
	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() {
		return this.userService.findAllUsers();
	}

	@PostMapping("/createUser")
	@PreAuthorize("permitAll()")
	public void createUser(@RequestBody User user) {
		if(this.roleService.findRoleById(user.getRoleId()).isPresent()) {
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			user.setEnabled(true);
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			this.userService.createUser(user);
		}
	}
	
	@PostMapping("/method1")
	@PreAuthorize("hasAuthority('READ')")
	public void method1() {
		System.out.println("Inside post method");
	}
	
	@DeleteMapping("/deleteUser")
	public void deleteUser() {
		
	}
}
