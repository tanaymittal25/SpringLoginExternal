package com.howtodoinjava.demo.resource;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.servlet.View;
import com.howtodoinjava.demo.resource.AuthorizationEndpoint;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;


@Controller
public class AcceptController extends AuthorizationRequest {
	
	final String AUTHORIZATION_REQUEST_ATTR_NAME = "authorizationRequest";
	final String redirectConsentPage = "http://localhost:8082/consentPage";
	
	@RequestMapping("/acceptPage")
	public String acceptPage() {
	    return "redirect:" + redirectConsentPage;
	}
	
	@SuppressWarnings("null")
	@RequestMapping("/authorize-success")
	public View authCode() {
		
		AuthorizationRequest authReq = null;
		
		
		Map<String, String> approvalParameters = new HashMap<String, String>();
		approvalParameters.put("user_oauth_approval", "true");
		approvalParameters.put("scope.read_profile_info", "true");
		approvalParameters.put("authorize", "Authorize");
		authReq.setApprovalParameters(approvalParameters);
		
		authReq.setRedirectUri("https://www.volantetech.com/");
		authReq.setClientId("clientapp");
		
		List<String> listScope = Arrays.asList("read_profile_info");
		authReq.setScope(listScope);
		
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("response_type", "code");
		requestParameters.put("client_id", "clientapp");
		requestParameters.put("scope", "read_profile_info");
		authReq.setRequestParameters(requestParameters);
		
		Authentication authUser = null;
		authUser.setAuthenticated(true);
		
		
		return AuthorizationEndpoint.getAuthorizationCodeResponse(authReq, authUser);
		
	}
	
}
