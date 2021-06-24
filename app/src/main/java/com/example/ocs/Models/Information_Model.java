package com.example.ocs.Models;

public class Information_Model {
    private String applyDate;
    private String proDate;
    private String proTime;
    private String proTitle;
    private long timestamp;

    public Information_Model() {
    }

    public Information_Model(String proTitle2, String applyDate2, String proDate2, String proTime2, long timestamp2) {
        this.proTitle = proTitle2;
        this.applyDate = applyDate2;
        this.proDate = proDate2;
        this.proTime = proTime2;
        this.timestamp = timestamp2;
    }

    public String getProTitle() {
        return this.proTitle;
    }

    public void setProTitle(String proTitle2) {
        this.proTitle = proTitle2;
    }

    public String getApplyDate() {
        return this.applyDate;
    }

    public void setApplyDate(String applyDate2) {
        this.applyDate = applyDate2;
    }

    public String getProDate() {
        return this.proDate;
    }

    public void setProDate(String proDate2) {
        this.proDate = proDate2;
    }

    public String getProTime() {
        return this.proTime;
    }

    public void setProTime(String proTime2) {
        this.proTime = proTime2;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp2) {
        this.timestamp = timestamp2;
    }

    public String toString() {
        return "Information_Model{proTitle='" + this.proTitle + '\'' + ", applyDate='" + this.applyDate + '\'' + ", proDate='" + this.proDate + '\'' + ", proTime='" + this.proTime + '\'' + ", timestamp=" + this.timestamp + '}';
    }
}
