package org.kpmp.logging;

import jakarta.servlet.http.HttpServletRequest;

import org.kpmp.shibboleth.ShibbolethUser;
import org.kpmp.shibboleth.ShibbolethUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

	private static final String UNKNOWN_USER = "Unknown user";
	private static final String UNKNOWN_URI = "Unknown URI";

	private static final String LOG_MESSAGE_FORMAT = "USER: {} | URI: {} | MSG: {} ";
	private ShibbolethUserService shibUserService;

	@Autowired
	public LoggingService(ShibbolethUserService shibUserService) {
		this.shibUserService = shibUserService;
	}

	@SuppressWarnings("rawtypes")
	public void logInfoMessage(Class clazz, String message, HttpServletRequest request) {
		Logger log = LoggerFactory.getLogger(clazz);
		log.info(LOG_MESSAGE_FORMAT, getUserString(request), getRequestURI(request), message);
	}

	@SuppressWarnings("rawtypes")
	public void logInfoMessage(Class clazz, ShibbolethUser user, String uri, String message) {
		Logger log = LoggerFactory.getLogger(clazz);
		log.info(LOG_MESSAGE_FORMAT, getUserString(user), uri, message);
	}

	@SuppressWarnings("rawtypes")
	public void logErrorMessage(Class clazz, String message, HttpServletRequest request) {
		Logger log = LoggerFactory.getLogger(clazz);
		log.error(LOG_MESSAGE_FORMAT, getUserString(request), getRequestURI(request), message);
	}

	@SuppressWarnings("rawtypes")
	public void logErrorMessage(Class clazz, ShibbolethUser user, String uri, String message) {
		Logger log = LoggerFactory.getLogger(clazz);
		log.error(LOG_MESSAGE_FORMAT, getUserString(user), uri, message);
	}

	@SuppressWarnings("rawtypes")
	public void logWarnMessage(Class clazz, String message, HttpServletRequest request) {
		Logger log = LoggerFactory.getLogger(clazz);
		log.warn(LOG_MESSAGE_FORMAT, getUserString(request), getRequestURI(request), message);
	}

	@SuppressWarnings("rawtypes")
	public void logWarnMessage(Class clazz, ShibbolethUser user, String uri, String message) {
		Logger log = LoggerFactory.getLogger(clazz);
		log.warn(LOG_MESSAGE_FORMAT, getUserString(user), uri, message);
	}

	private String getUserString(ShibbolethUser user) {
		return user != null ? user.toString() : UNKNOWN_USER;
	}

	private String getUserString(HttpServletRequest request) {
		return request != null ? getUserString(shibUserService.getUser(request)) : UNKNOWN_USER;
	}

	private String getRequestURI(HttpServletRequest request) {
		return request != null ? request.getRequestURI() : UNKNOWN_URI;
	}

}