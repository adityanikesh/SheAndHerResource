package com.cvs.avocado.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.cvs.avocado.models.Role;
import com.cvs.avocado.models.User;
import com.cvs.avocado.repositories.RoleRepository;
import com.cvs.avocado.repositories.UserRepository;
import com.cvs.avocado.security.AuthenticationFacade;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	TokenStore tokenStore;
	
	@Autowired
	AuthenticationFacade authenticationFacade;
	
	public List<User> findAllUsers() {
		return this.userRepository.findAll();
	}
	
	public boolean createUser(User user) {
		User userDB = this.userRepository.save(user);
		if(userDB != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user.getRoleId()));
	}
	
	private List<SimpleGrantedAuthority> getAuthority(long roleId) {
		List <SimpleGrantedAuthority> listAuthority = null;
		Optional<Role> role = this.roleRepository.findById(roleId);
		if (role.isPresent()) {
			String[] authorities = role.get().getAuthorities().split(",");
			listAuthority = new ArrayList<SimpleGrantedAuthority>();
			for(String authority: authorities) {
				listAuthority.add(new SimpleGrantedAuthority(authority.trim()));
			}
		}
		return listAuthority;
	}
	
	public void logout() {
		OAuth2AccessToken token = this.tokenStore.getAccessToken(authenticationFacade.getOAuth2Authentication());
		this.tokenStore.removeAccessToken(token);
		this.tokenStore.removeRefreshToken(token.getRefreshToken());
	}

}
