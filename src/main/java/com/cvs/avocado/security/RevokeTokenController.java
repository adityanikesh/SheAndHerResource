package com.cvs.avocado.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RevokeTokenController {

	@Autowired
	AuthenticationFacade authenticationFacade;

	@Autowired
	TokenStore tokenStore;

	@PostMapping("/revokeToken")
	@PreAuthorize("hasAuthority('READ')")
	public void revokeToken() {
		OAuth2AccessToken accessToken = this.tokenStore.getAccessToken(this.authenticationFacade.getOAuth2Authentication());
		tokenStore.removeAccessToken(accessToken);
		tokenStore.removeRefreshToken(accessToken.getRefreshToken());
	}

}
