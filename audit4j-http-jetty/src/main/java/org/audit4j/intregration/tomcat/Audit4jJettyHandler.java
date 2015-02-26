package org.audit4j.intregration.tomcat;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.audit4j.core.AuditManager;
import org.audit4j.core.dto.EventBuilder;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

/**
 * The Class Audit4jJettyHandler.
 * 
 * For Jetty 8,9+
 * 
 * Server Mode:
 * 
 * Put audit4j-core-2.3.1.jar(or higher) and audit4j-tomcat-2.3.1.jar in the
 * jetty 'lib/etc' directory.
 * 
 * <pre>
 * {@code
 *    <Set name="handler">
 *     ...
 *     <Item>
 *      <New id="JAMonHandler" class="org.audit4j.intregration.tomcat.Audit4jJettyHandler"/>
 *     </Item> 
 *   </Set> 
 *   }
 * </pre>
 * 
 * Embedded Mode:
 * 
 * <pre>
 * {@code
 * Server server=new Server(8080);
 * ...
 * Audit4jJettyHandler wrapper =  new Audit4jJettyHandler();
 * server.setHandler(wrapper);
 *   server.start();
 *   }
 * </pre>
 * 
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Audit4jJettyHandler extends HandlerWrapper {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jetty.server.handler.HandlerWrapper#handle(java.lang.String,
     * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
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
        super.handle(target, baseRequest, request, response);
    }
}
