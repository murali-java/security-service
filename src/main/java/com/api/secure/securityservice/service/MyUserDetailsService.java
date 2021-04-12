package com.api.secure.securityservice.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MyUserDetailsService implements UserDetailsService {
    Map<String, User> map;

    @PostConstruct
    public void init(){
        map = new HashMap<>();
        SimpleGrantedAuthority user = new SimpleGrantedAuthority("ROLE_USER");
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ROLE_ADMIN");
        User user1 = new User("one", "one", Stream.of(user, admin).collect(Collectors.toList()));
        map.put(user1.getUsername(), user1);
        user1 = new User("two", "two", Stream.of(user).collect(Collectors.toList()));
        map.put(user1.getUsername(), user1);
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return map.get(s);
    }
}
