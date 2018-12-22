package com.cvs.avocado.security;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HttpServletFacade {
	
	public String getRemoteAddress() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
	}
	
	public String getRemoteHost() {
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getRemoteHost();
	}
}