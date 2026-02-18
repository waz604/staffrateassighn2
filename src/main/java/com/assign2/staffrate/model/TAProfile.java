package com.assign2.staffrate.model;

public class TAProfile implements StaffMemberProfile {

    public String displayTitle(String name){
        return "TA" + name;
    }
}
