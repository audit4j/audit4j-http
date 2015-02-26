package org.audit4j.intregration.tomcat;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.audit4j.core.AuditManager;
import org.audit4j.core.dto.EventBuilder;
import org.glassfish.web.valve.GlassFishValve;

/**
 * The Class Audit4jGlashfishValue.
 * 
 * For glashfish 4.0+
 * 
 *<pre>{@code
 *  <sun-web-app ...>
     ...
     <property name="valve_1" value="org.glassfish.extension.Valve"/>
   </sun-web-app>
 *}
 *</pre>
 *
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Audit4jGlashfishValue implements GlassFishValve {

    /* (non-Javadoc)
     * @see org.glassfish.web.valve.GlassFishValve#getInfo()
     */
    @Override
    public String getInfo() {
        return "Audit4j Glassfish integration";
    }

    /* (non-Javadoc)
     * @see org.glassfish.web.valve.GlassFishValve#invoke(org.apache.catalina.Request, org.apache.catalina.Response)
     */
    @Override
    public int invoke(Request request, Response arg1) throws IOException, ServletException {
        String ipAddress = request.getRequest().getRemoteAddr();
        String url = request.getRequest().getRemoteHost();

        EventBuilder builder = new EventBuilder();
        builder.addAction(url).addOrigin(ipAddress);
        builder.addActor(ipAddress);

        Map<String, String[]> params = request.getRequest().getParameterMap();

        for (final Map.Entry<String, String[]> entry : params.entrySet()) {
            builder.addField(entry.getKey(), entry.getValue());
        }
        AuditManager.getInstance().audit(builder.build());
        return GlassFishValve.INVOKE_NEXT;
    }

    /* (non-Javadoc)
     * @see org.glassfish.web.valve.GlassFishValve#postInvoke(org.apache.catalina.Request, org.apache.catalina.Response)
     */
    @Override
    public void postInvoke(Request arg0, Response arg1) throws IOException, ServletException {
        // TODO Auto-generated method stub
        
    }

}
