package com.moodle.tenant.model;

/**
 * Created by andrewlarsen on 9/10/17.
 */
public class CFStack {

    private String stackName;
    private String url;
    private String status;
    private Long creationTime;

    public CFStack() {
    }

    public CFStack(String stackName, String url, String status, Long creationTime) {
        this.stackName = stackName;
        this.url = url;
        this.status = status;
        this.creationTime = creationTime;
    }

    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }
}
