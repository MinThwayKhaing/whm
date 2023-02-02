/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dat.whm.web.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class BootStrap implements ServletContextListener {
	private static Logger logger = Logger.getLogger(BootStrap.class);

	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Initialization has been started...................");
		String systemPath = sce.getServletContext().getRealPath("/") + "/upload";
		new Scheduler(systemPath);
		logger.info("Initilization has been fiished......................");
	}
	public void contextDestroyed(ServletContextEvent sce) {
	}
}