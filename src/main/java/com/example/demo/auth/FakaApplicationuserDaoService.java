package com.example.demo.auth;

import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.security.ApplicationUserRole.*;

@Repository("fake")
public class FakaApplicationuserDaoService implements ApplicationUserDao{
    
    private final PasswordEncoder passwordEncoder;
    
    public FakaApplicationuserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
        return  getApplicationUsers()
                .stream()
                .filter(applicationUser -> applicationUser.getUsername().equals(username))
                .findFirst();
    }
    
    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> users = Lists.newArrayList(
                new ApplicationUser(
                        STUDENT.getGrantedAuthorities(),
                        passwordEncoder.encode("passwor"),
                        "y-ouldbouchiba",
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMIN.getGrantedAuthorities(),
                        passwordEncoder.encode("passwor"),
                        "a-admin",
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        ADMINTRAINEE.getGrantedAuthorities(),
                        passwordEncoder.encode("passwor"),
                        "a-trainee",
                        true,
                        true,
                        true,
                        true
                )
                
        );
        return users;
    }
}
