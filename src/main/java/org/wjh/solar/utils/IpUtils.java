package org.wjh.solar.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * IP工具类
 * 
 * @author wangjihui
 *
 */
public class IpUtils {

    public static String getIp(HttpServletRequest request) {
        return getRemoteIpAddress(request);
    }

    public static String getRemoteIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (!isIpValid(ip)) {
            ip = request.getHeader("X-From-IP");
        }
        if (!isIpValid(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if (!isIpValid(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.length() > 15) {// 多层代理取第一个ip地址
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    private static boolean isIpValid(String ip) {
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            return false;
        } else {
            return true;
        }
    }
}
