package eu.zeigermann.gwt.demo.server.boundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.zeigermann.gwt.demo.server.service.ShoppingListService;

@SuppressWarnings("serial")
public class StatisticsRestEndpoint extends AbstractSpringServlet {

	private Logger logger = Logger.getLogger(StatisticsRestEndpoint.class
			.getName());

	@Inject
	ShoppingListService service;

	@Inject
	ObjectMapper mapper;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String, Integer> statistics = service.statistics();
		String callback = req.getParameter("callback");
		passResult(resp, statistics, callback);
		if (callback == null) {
			setCORSHeaders(resp);
		} else {
			logger.info("Callback provided by browser / GWT: " + callback);
			setJsonpHeaders(resp);
		}
	}

	private void setJsonpHeaders(HttpServletResponse resp) {
		resp.setHeader("Content-Type", "text/javascript");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
	}

	private void passResult(HttpServletResponse resp, Object result,
			String callback) {
		try {
			String json = mapper.writeValueAsString(result);
			if (callback != null) {
				json = callback+"(" + json + ");";
			}
			logger.info("Statistics result: " + json);
			resp.getWriter().write(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String origin = req.getHeader("Origin");
		logger.info("Performing OPTIONS with Origin: " + origin);
		setCORSHeaders(resp);
	}

	// http://en.wikipedia.org/wiki/Cross-Origin_Resource_Sharing
	// https://developer.mozilla.org/en-US/docs/HTTP/Access_control_CORS?redirectlocale=en-US&redirectslug=HTTP_access_control#Access-Control-Allow-Headers
	private void setCORSHeaders(HttpServletResponse resp) {
		// allow for cross site origin
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods",
				"POST, GET, PUT, DELETE, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers",
				"ACCEPT, ORIGIN, X-REQUESTED-WITH, CONTENT-TYPE");
	}

}
