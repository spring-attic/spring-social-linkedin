package org.springframework.social.linkedin.connect;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public class LinkedinConnectInterceptor implements ConnectInterceptor<LinkedIn> {
	String state;
	String scope;
	
	
	public LinkedinConnectInterceptor(String state) {
		this(state, "r_basicprofile");
	}
	
	public LinkedinConnectInterceptor(String state,String scope) {
		super();
		this.state = state;
		this.scope = scope;
	}

	@Override
	public void preConnect(ConnectionFactory<LinkedIn> connectionFactory,
			MultiValueMap<String, String> parameters, WebRequest request) {
		parameters.add("state", state);
		parameters.add("scope",scope);
	}

	@Override
	public void postConnect(Connection<LinkedIn> connection, WebRequest request) {

	}

}
