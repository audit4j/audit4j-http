package org.audit4j.intregration.tomcat;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.audit4j.core.AuditManager;
import org.audit4j.core.dto.EventBuilder;
import org.mortbay.jetty.handler.HandlerWrapper;

/**
 * The Class Audit4jJetty6Handler.
 * 
 * For Jetty 6.1.5
 * 
 * Server Mode:
 * 
 * Put audit4j-core-2.3.1.jar(or higher) and audit4j-tomcat-2.3.1.jar in the
 * jetty 'lib/etc' directory.
 * 
 * <pre>
 * {@code
 * <Set name="handler">
 *   ...
 *   <Item>
 *    <New id="JAMonHandler" class="org.audit4j.intregration.tomcat.Audit4jJetty6Handler"/>
 *   </Item> 
 * </Set> 
 * }
 * 
 * </pre>
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Audit4jJetty6Handler extends HandlerWrapper {

    /*
     * (non-Javadoc)
     * 
     * @see org.mortbay.jetty.handler.HandlerWrapper#handle(java.lang.String,
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, int)
     */
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
            throws IOException, ServletException {
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
        super.handle(target, request, response, dispatch);
    }
}
