package com.bean;

import com.util.PageTool;

public class Efunction extends PageTool {
    private Long fid;

    private String fcode;

    private String fname;

    private String url;

    private Long parentid;

    private String remark1;

    private String remark2;

    private Byte flevel;

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public Byte getFlevel() {
        return flevel;
    }

    public void setFlevel(Byte flevel) {
        this.flevel = flevel;
    }
}