package org.audit4j.intregration.http;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.audit4j.core.AuditManager;

/**
 * The listener interface for receiving auditContext events. The class that is
 * interested in processing a auditContext event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addAuditContextListener<code> method. When
 * the auditContext event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see AuditContextEvent
 */
public class AuditContextListener implements ServletContextListener {

    private ServletContexConfigSupport configSupport = null;

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
     * .ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        configSupport = new ServletContexConfigSupport();
        if (configSupport.hasHandlers(contextEvent.getServletContext())) {
            AuditManager.getInstance();
        } else {
            AuditManager.initWithConfiguration(configSupport.loadConfig(contextEvent.getServletContext()));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
     * ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        AuditManager.getInstance().shutdown();
    }

}
