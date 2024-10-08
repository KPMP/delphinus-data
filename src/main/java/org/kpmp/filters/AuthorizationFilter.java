package org.kpmp.filters;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kpmp.logging.LoggingService;
import org.kpmp.shibboleth.ShibbolethUser;
import org.kpmp.shibboleth.ShibbolethUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthorizationFilter implements Filter {

	private static final String USER_NOT_PART_OF_KPMP = "User is not part of KPMP: ";
	private static final String USER_NO_DPR_ACCESS = "User does not have access to DPR: ";
	private static final String GROUPS_KEY = "groups";
	private static final String USER_DOES_NOT_EXIST = "User does not exist in User Portal: ";
	private static final String CLIENT_ID_PROPERTY = "CLIENT_ID";
	private static final String COOKIE_NAME = "shibid";
	private static final int SECONDS_IN_MINUTE = 60;
	private static final int MINUTES_IN_HOUR = 60;
	private static final int SESSION_TIMEOUT_HOURS = 8;
	private static final int SESSION_TIMEOUT_SECONDS = SECONDS_IN_MINUTE * MINUTES_IN_HOUR * SESSION_TIMEOUT_HOURS;

	private LoggingService logger;
	private ShibbolethUserService shibUserService;
	private RestTemplate restTemplate;

	@Value("${user.auth.host}")
	private String userAuthHost;
	@Value("${user.auth.endpoint}")
	private String userAuthEndpoint;
	@Value("#{'${user.auth.allowed.groups}'.split(',')}")
	private List<String> allowedGroups;
	@Value("${user.auth.kpmp.group}")
	private String kpmpGroup;
	private Environment env;

	@Autowired
	public AuthorizationFilter(LoggingService logger, ShibbolethUserService shibUserService, RestTemplate restTemplate,
			Environment env) {
		this.logger = logger;
		this.shibUserService = shibUserService;
		this.restTemplate = restTemplate;
		this.env = env;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.logInfoMessage(this.getClass(), null, this.getClass().getSimpleName() + ".init",
				"Initializing filter: " + this.getClass().getSimpleName());
	}

	@Override
	public void doFilter(ServletRequest incomingRequest, ServletResponse incomingResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) incomingRequest;
		HttpServletResponse response = (HttpServletResponse) incomingResponse;

		Cookie[] cookies = request.getCookies();
		ShibbolethUser user = shibUserService.getUser(request);
		String shibId = user.getShibId();

		if (hasExistingSession(shibId, cookies, request)) {
			chain.doFilter(request, response);
		} else {
			String clientId = env.getProperty(CLIENT_ID_PROPERTY);
			String uri = userAuthHost + userAuthEndpoint + "/" + clientId + "/" + shibId;
			try {
				ResponseEntity<String> userInfoResponse = restTemplate.getForEntity(uri, String.class);
				String userInfo = userInfoResponse.getBody();
				try {
					JSONObject userJson = new JSONObject(userInfo);
					JSONArray userGroups = userJson.getJSONArray(GROUPS_KEY);

					if (isAllowed(userGroups) && userJson.getBoolean("active")) {
						HttpSession session = request.getSession(true);
						session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);
						Cookie message = new Cookie(COOKIE_NAME, shibId);
						session.setAttribute("roles", userGroups);
						response.addCookie(message);
						chain.doFilter(request, response);
					} else if (isKPMP(userGroups)) {
						handleError(USER_NO_DPR_ACCESS + userGroups, HttpStatus.FORBIDDEN, request, response);
					} else {
						handleError(USER_NOT_PART_OF_KPMP + userGroups, HttpStatus.NOT_FOUND, request, response);
					}

				} catch (JSONException e) {
					handleError("Unable to parse response from User Portal, denying user " + shibId
							+ " access.  Response: " + userInfo, HttpStatus.FAILED_DEPENDENCY, request, response);

				}

			} catch (HttpClientErrorException e) {
				int statusCode = e.getRawStatusCode();
				if (statusCode == HttpStatus.NOT_FOUND.value()) {
					handleError(USER_DOES_NOT_EXIST + shibId, HttpStatus.NOT_FOUND, request, response);
				} else if (statusCode != HttpStatus.OK.value()) {
					handleError("Unable to get user information. User auth returned status code: " + statusCode,
							HttpStatus.FAILED_DEPENDENCY, request, response);
				}
			}
		}

	}

	private boolean isAllowed(JSONArray userGroups) throws JSONException {
		for (int i = 0; i < userGroups.length(); i++) {
			String group = userGroups.getString(i);
			if (allowedGroups.contains(group)) {
				return true;
			}
		}
		return false;
	}

	private boolean isKPMP(JSONArray userGroups) throws JSONException {
		for (int i = 0; i < userGroups.length(); i++) {
			String group = userGroups.getString(i);
			if (kpmpGroup.equals(group)) {
				return true;
			}
		}
		return false;
	}

	private void handleError(String errorMessage, HttpStatus status, HttpServletRequest request,
			HttpServletResponse response) {
		logger.logErrorMessage(this.getClass(), errorMessage, request);
		response.setStatus(status.value());
	}

	private boolean hasExistingSession(String shibId, Cookie[] cookies, HttpServletRequest request) {
		HttpSession existingSession = request.getSession(false);
		if (existingSession != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("shibId")) {
					if (cookie.getValue().equals(shibId)) {
						return true;
					} else {
						logger.logInfoMessage(this.getClass(),
								"MSG: Invalidating session. Cookie does not match shibId for user", request);
						existingSession.invalidate();
						return false;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void destroy() {
		logger.logInfoMessage(this.getClass(), "Destroying filter: AuthorizationFilter", null);
	}

}
