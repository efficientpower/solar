package org.wjh.solar.domain.mongo;

import java.io.Serializable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexes;

@Entity(value = "ip", noClassnameStored = true)
@Indexes({ @Index(value = "ip", unique = true) })
public class Ip implements Serializable {

    private static final long serialVersionUID = 3023494683388845033L;

    // ip
    @Id
    private String ip;
    // 运营商
    private String isp;
    // 运营商代号
    private String isp_id;
    // 国家
    private String country;
    // 国家代号
    private String country_id;
    // 地区
    private String area;
    // 地区代号
    private String area_id;
    // 一级行政区域(省,直辖市,特别行政区)
    private String region;
    // 一级行政区域代号(省,直辖市,特别行政区)
    private String region_id;
    // 二级行政区域(地级市)
    private String city;
    // 二级行政区域代号(地级市)
    private String city_id;
    // 三级行政区域
    private String county;
    // 三级行政区域代号
    private String county_id;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getIsp_id() {
        return isp_id;
    }

    public void setIsp_id(String isp_id) {
        this.isp_id = isp_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty_id() {
        return county_id;
    }

    public void setCounty_id(String county_id) {
        this.county_id = county_id;
    }

}
