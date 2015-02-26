package org.audit4j.intregration.http.Int;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.audit4j.intregration.http.AuditFilter;
import org.junit.Test;

public class HTTPIntTest {

    @Test
    public void testWithContextConfig() {
        
    }
    
    
    @Test
    public void testWithoutContextConfig() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("username")).thenReturn("me");
        when(request.getParameter("password")).thenReturn("secret");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://audit4j.org"));
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
        Map<String, String[]> params = new HashMap<>();
        String[] param = {"me"};
        params.put("username", param);
        when(request.getParameterMap()).thenReturn(params);
        
        
        FilterChain filterChain = mock(FilterChain.class);
        
        new AuditFilter().doFilter(request, response, filterChain);

    }
}
