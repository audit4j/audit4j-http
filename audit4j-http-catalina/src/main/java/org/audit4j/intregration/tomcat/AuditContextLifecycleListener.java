package org.audit4j.intregration.tomcat;

import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.audit4j.core.AuditManager;

/**
 *The Class Lifecycle event.
 *
 *For Tomcat 6,7,8+
 *
 *For JBoss 4.0.5/4.2
 *
 *<p>
 *Put audit4j-core-2.3.1.jar(or higher) and audit4j-tomcat-2.3.1.jar in the tomcat 'lib' directory. 
 *</p>
 *Add the following Valve line to Tomcat's server.xml file. The 'Engine' line is used to show context.
 *<pre>
 *{@code
<Listener className="org.audit4j.intregration.tomcat.AuditContextLifecycleListener"/>
 }
 </pre>
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.1
 */
public class AuditContextLifecycleListener implements LifecycleListener {

    /* (non-Javadoc)
     * @see org.apache.catalina.LifecycleListener#lifecycleEvent(org.apache.catalina.LifecycleEvent)
     */
    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        try {
            if (Lifecycle.START_EVENT.equals(event.getType())) {
                AuditManager.getInstance();
            } else if (Lifecycle.BEFORE_STOP_EVENT.equals(event.getType())) {
                AuditManager.getInstance().shutdown();
            }
        } catch (final Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
