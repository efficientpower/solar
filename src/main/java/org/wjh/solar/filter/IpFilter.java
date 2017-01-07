package org.wjh.solar.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.wjh.solar.jms.IpNotifier;
import org.wjh.solar.utils.IpUtils;
import org.wjh.solar.utils.SpringContextUtils;

/**
 * 记录用户IP过滤器
 * 
 * @author wangjihui
 *         
 */
public class IpFilter implements Filter {
    
    private FilterConfig config;
    
    @Override
    public void init(FilterConfig config) throws ServletException {
        // TODO Auto-generated method stub
        this.config = config;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        IpNotifier ipNotifier = SpringContextUtils.getBean(IpNotifier.class);
        ipNotifier.send(IpUtils.getIp((HttpServletRequest) request));
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }
    
    public FilterConfig getFilterConfig() {
        return config;
    }
    
}
