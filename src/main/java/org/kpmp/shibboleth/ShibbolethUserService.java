package org.kpmp.shibboleth;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.kpmp.logging.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShibbolethUserService {

	private UTF8Encoder encoder;
	private LoggingService logger;

	@Autowired
	public ShibbolethUserService(UTF8Encoder encoder, LoggingService logger) {
		this.encoder = encoder;
		this.logger = logger;
	}

	public ShibbolethUser getUser(HttpServletRequest request) {

		ShibbolethUser user = new ShibbolethUser();

		if (request == null) {
			return null;
		}

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String header = headerNames.nextElement();
			logger.logInfoMessage(this.getClass(), header + ": " + request.getIntHeader(header), request);
		}

		String value = handleNull(request.getHeader("mail"));
		String email = encoder.convertFromLatin1(value);
		value = handleNull(request.getHeader("displayname"));
		String displayName = encoder.convertFromLatin1(value);
		value = handleNull(request.getHeader("givenname"));
		String firstName = encoder.convertFromLatin1(value);
		value = handleNull(request.getHeader("sn"));
		String lastName = encoder.convertFromLatin1(value);
		value = handleNull(request.getHeader("eppn"));
		String shibId = encoder.convertFromLatin1(value);

		user.setDisplayName(displayName);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setEmail(email);
		user.setShibId(shibId);

		return user;

	}

	private String handleNull(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}
}