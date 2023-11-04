package com.example.demo.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet(ApplicationUserPermission.STUDENT_READ ,
            ApplicationUserPermission.COURSE_READ ,
            ApplicationUserPermission.STUDENT_WRITE )),
    ADMIN(Sets.newHashSet(ApplicationUserPermission.STUDENT_READ ,
            ApplicationUserPermission.COURSE_READ ,
            ApplicationUserPermission.STUDENT_WRITE ,
            ApplicationUserPermission.COURSE_WRITE)),
    
    ADMINTRAINEE(Sets.newHashSet(ApplicationUserPermission.STUDENT_READ ,
          ApplicationUserPermission.COURSE_READ ));
    
    private final Set<ApplicationUserPermission> permissions;
    
    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
    
    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }
    
    public Set<GrantedAuthority> getGrantedAuthorities(){
        Set<GrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getLabel()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
