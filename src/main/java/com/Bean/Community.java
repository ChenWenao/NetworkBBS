package com.Bean;

import java.util.Date;

public class Community {
    private int communityId;
    private String communityName;
    private String communityIcon;
    private String communityIntroduction;
    private Date communityBuildTime;
    private int communityHeat;
    private int communitySize;
    private int communityOwnerId;

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityIcon() {
        return communityIcon;
    }

    public void setCommunityIcon(String communityIcon) {
        this.communityIcon = communityIcon;
    }

    public String getCommunityIntroduction() {
        return communityIntroduction;
    }

    public void setCommunityIntroduction(String communityIntroduction) {
        this.communityIntroduction = communityIntroduction;
    }

    public Date getCommunityBuildTime() {
        return communityBuildTime;
    }

    public void setCommunityBuildTime(Date communityBuildTime) {
        this.communityBuildTime = communityBuildTime;
    }

    public int getCommunityHeat() {
        return communityHeat;
    }

    public void setCommunityHeat(int communityHeat) {
        this.communityHeat = communityHeat;
    }

    public int getCommunitySize() {
        return communitySize;
    }

    public void setCommunitySize(int communitySize) {
        this.communitySize = communitySize;
    }

    public int getCommunityOwnerId() {
        return communityOwnerId;
    }

    public void setCommunityOwnerId(int communityOwnerId) {
        this.communityOwnerId = communityOwnerId;
    }
}
