package org.audit4j.intregration.tomcat;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.audit4j.core.AuditManager;
import org.audit4j.core.dto.EventBuilder;

/**
 *The Class Audit4jTomcatValve.
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
 <Engine name="Catalina" defaultHost="localhost">
 <Valve className="org.audit4j.intregration.tomcat.Audit4jTomcatValve"/>
 }
 </pre>
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.1
 * 
 */
public class Audit4jTomcatValve extends ValveBase {

    /* (non-Javadoc)
     * @see org.apache.catalina.valves.ValveBase#invoke(org.apache.catalina.connector.Request, org.apache.catalina.connector.Response)
     */
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        String ipAddress = request.getRemoteAddr();
        String url = request.getRequestURL().toString();

        EventBuilder builder = new EventBuilder();
        builder.addAction(url).addOrigin(ipAddress);
        builder.addActor(ipAddress);

        Map<String, String[]> params = request.getParameterMap();

        for (final Map.Entry<String, String[]> entry : params.entrySet()) {
            builder.addField(entry.getKey(), entry.getValue());
        }
        AuditManager.getInstance().audit(builder.build());
        getNext().invoke(request, response);
    }
}
