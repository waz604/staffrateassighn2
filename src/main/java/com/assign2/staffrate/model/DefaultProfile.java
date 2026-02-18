package com.assign2.staffrate.model;

public class DefaultProfile implements StaffMemberProfile {
    private final StaffRoleType roleType;

    public DefaultProfile(StaffRoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String displayTitle(String name) {
        return roleType.name() + " " + name;
    }
}
