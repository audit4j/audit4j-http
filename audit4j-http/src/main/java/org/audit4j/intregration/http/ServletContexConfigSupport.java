package org.audit4j.intregration.http;

import javax.servlet.ServletContext;

import org.audit4j.core.Configuration;
import org.audit4j.core.MetaData;
import org.audit4j.core.filter.AuditEventFilter;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.layout.Layout;
import org.audit4j.core.util.ReflectUtil;

/**
 * The Class ServletContexConfigSupport.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
class ServletContexConfigSupport {

    /**
     * Load config.
     *
     * @param servletContext the servlet context
     * @return the configuration
     */
    Configuration loadConfig(ServletContext servletContext) {
        String handlers = servletContext.getInitParameter(ContextConfigParams.PARAM_HANDLERS);
        String layout = servletContext.getInitParameter(ContextConfigParams.PARAM_LAYOUT);
        String filters = servletContext.getInitParameter(ContextConfigParams.PARAM_FILTERS);
        String options = servletContext.getInitParameter(ContextConfigParams.PARAM_OPTIONS);
        String metaData = servletContext.getInitParameter(ContextConfigParams.PARAM_META_DATA);
        String properties = servletContext.getInitParameter(ContextConfigParams.PARAM_PROPERTIES);

        Configuration config = new Configuration();
        String[] handlerClassList = handlers.split(";");
        for (String handlerClassName : handlerClassList) {
            ReflectUtil<Handler> util = new ReflectUtil<>();
            Handler handler = util.getNewInstance(handlerClassName);
            config.addHandler(handler);
        }
        config.setLayout(new ReflectUtil<Layout>().getNewInstance(layout));
        if (filters != null && !filters.equals("")) {
            String[] filterClassList = filters.split(";");
            for (String filterClassName : filterClassList) {
                ReflectUtil<AuditEventFilter> util = new ReflectUtil<>();
                AuditEventFilter filter = util.getNewInstance(filterClassName);
                config.addFilter(filter);
            }
        }
        
        config.setOptions(options);
        config.setMetaData(new ReflectUtil<MetaData>().getNewInstance(metaData));
        String[] propertiesList = properties.split(";");
        for (String property : propertiesList) {
            String[] keyValue = property.split(":");
            config.addProperty(keyValue[0], keyValue[1]);
        }
        return config;
    }

    /**
     * Checks for handlers.
     *
     * @param servletContext the servlet context
     * @return true, if successful
     */
    boolean hasHandlers(ServletContext servletContext) {
        String handlers = servletContext.getInitParameter("handlers");
        if (handlers == null || handlers.equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
