package com.cvs.avocado.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

	public Authentication getAuthentication() {
		return (OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication();
	}
	
	public OAuth2Authentication getOAuth2Authentication() {
		return (OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication();
	}
	
	public String getCurrentUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
