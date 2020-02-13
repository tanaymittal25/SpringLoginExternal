package com.howtodoinjava.demo.resource;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.View;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class AcceptController {
	
	/*@RequestMapping(value = "/accept", method = {RequestMethod.POST, RequestMethod.GET})
	public String accept() {
		System.out.print("\nHello");
		return "approve.html";
	}*/
	
	static final String AUTHORIZATION_REQUEST_ATTR_NAME = "authorizationRequest";
	
	@RequestMapping("/acceptPage")
	public String acceptPage() {
	    return "approve.html";
	}
	
	/*@Override
	@RequestMapping(value = "/oauth/authorize", method = RequestMethod.POST, params = OAuth2Utils.USER_OAUTH_APPROVAL)
    public org.springframework.web.servlet.View approveOrDeny(Map<String, String> approvalParameters, Map<String, ?> model, SessionStatus sessionStatus, Principal principal) {
		
		AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get(AUTHORIZATION_REQUEST_ATTR_NAME);
		Set<String> responseTypes = authorizationRequest.getResponseTypes();

		if (!responseTypes.contains("token") && !responseTypes.contains("code")) {
			throw new UnsupportedResponseTypeException("Unsupported response types: " + responseTypes);
		}
		return getAuthorizationCodeResponse(authorizationRequest, (Authentication) principal);
	}*/
}
