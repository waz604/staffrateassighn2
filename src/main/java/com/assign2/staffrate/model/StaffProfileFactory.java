package com.assign2.staffrate.model;

public class StaffProfileFactory {
    public StaffMemberProfile forRole(StaffRoleType roletype){
        if(roletype == null){
            return new DefaultProfile(roletype);
        }else if(roletype == StaffRoleType.TA){
             return new TAProfile();
        }else if(roletype == StaffRoleType.PROF){
           return new ProfProfile();
        }else{
        return new DefaultProfile(roletype);
        }
    }
    
}
