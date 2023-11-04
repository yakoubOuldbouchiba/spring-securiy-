package com.example.demo.security;

public enum ApplicationUserPermission
{
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");
    
    public String getLabel() {
        return label;
    }
    
    private final String label;
    
    
    ApplicationUserPermission(String label) {
        this.label = label;
    }
}
