package org.wjh.solar.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import sun.net.util.IPAddressUtil;

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
        isPublicIp(ip);
        return ip;
    }

    private static boolean isIpValid(String ip) {
        if (StringUtils.isBlank(ip) 
                || "unknown".equalsIgnoreCase(ip) 
                || "127.0.0.1".equalsIgnoreCase(ip)
                || "0:0:0:0:0:0:0:1".equalsIgnoreCase(ip)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isPublicIp(String ip) {
        byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
        if(addr == null){
            return false;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
        case SECTION_1:
            return false;
        case SECTION_2:
            if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                return false;
            }
        case SECTION_5:
            switch (b1) {
            case SECTION_6:
                return false;
            }
        default:
            return true;
        }
    }
}
