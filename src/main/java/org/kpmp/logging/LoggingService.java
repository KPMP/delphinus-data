package org.kpmp.logging;

import javax.servlet.http.HttpServletRequest;

import org.kpmp.shibboleth.ShibbolethUser;
import org.kpmp.shibboleth.ShibbolethUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

	private static final String LOG_MESSAGE_FORMAT = "USER: {} | URI: {} | MSG: {} ";
	private ShibbolethUserService shibUserService;

	@Autowired
	public LoggingService(ShibbolethUserService shibUserService) {
		this.shibUserService = shibUserService;
	}

	@SuppressWarnings("rawtypes")
	public void logInfoMessage(Class clazz, String message, HttpServletRequest request) {
		Logger log = LoggerFactory.getLogger(clazz);
		ShibbolethUser user = shibUserService.getUser(request);
		if (user == null) {
			log.info(LOG_MESSAGE_FORMAT, "Unknown user", request.getRequestURI(), message);
		} else {
			log.info(LOG_MESSAGE_FORMAT, user.toString(), request.getRequestURI(), message);
		}
	}

	@SuppressWarnings("rawtypes")
	public void logInfoMessage(Class clazz, ShibbolethUser user, String uri, String message) {
		Logger log = LoggerFactory.getLogger(clazz);
		if (user == null) {
			log.info(LOG_MESSAGE_FORMAT, "Unknown user", uri, message);
		} else {
			log.info(LOG_MESSAGE_FORMAT, user.toString(), uri, message);
		}
	}

	@SuppressWarnings("rawtypes")
	public void logErrorMessage(Class clazz, String message, HttpServletRequest request) {
		Logger log = LoggerFactory.getLogger(clazz);
		ShibbolethUser user = shibUserService.getUser(request);
		if (user == null) {
			log.error(LOG_MESSAGE_FORMAT, "Unknown user", request.getRequestURI(), message);
		} else {
			log.error(LOG_MESSAGE_FORMAT, user.toString(), request.getRequestURI(), message);
		}
	}

	@SuppressWarnings("rawtypes")
	public void logErrorMessage(Class clazz, ShibbolethUser user, String uri, String message) {
		Logger log = LoggerFactory.getLogger(clazz);
		if (user == null) {
			log.error(LOG_MESSAGE_FORMAT, "Unknown user", uri, message);
		} else {
			log.error(LOG_MESSAGE_FORMAT, user.toString(), uri, message);
		}
	}

	@SuppressWarnings("rawtypes")
	public void logWarnMessage(Class clazz, String message, HttpServletRequest request) {
		Logger log = LoggerFactory.getLogger(clazz);
		ShibbolethUser user = shibUserService.getUser(request);
		if (user == null) {
			log.warn(LOG_MESSAGE_FORMAT, "Unknown user", request.getRequestURI(), message);
		} else {
			log.warn(LOG_MESSAGE_FORMAT, user.toString(), request.getRequestURI(), message);
		}
	}

	@SuppressWarnings("rawtypes")
	public void logWarnMessage(Class clazz, ShibbolethUser user, String uri, String message) {
		Logger log = LoggerFactory.getLogger(clazz);
		if (user == null) {
			log.warn(LOG_MESSAGE_FORMAT, "Unknown user", uri, message);
		} else {
			log.warn(LOG_MESSAGE_FORMAT, user.toString(), uri, message);
		}
	}

}