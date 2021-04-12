package com.api.secure.securityservice;

import com.api.secure.securityservice.model.TokenRequest;
import com.api.secure.securityservice.service.MyUserDetailsService;
import com.api.secure.securityservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class TokenApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/token")
    public String getToken(@RequestBody TokenRequest request){
       Authentication auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
               (request.getUser(), request.getPwd()));
        UserDetails user = myUserDetailsService.loadUserByUsername(request.getUser());
        return jwtUtil.generateToken(user);
    }

    @PostMapping("/validate")
    public User getToken(@RequestHeader String token){
        UserDetails user = myUserDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
        if(jwtUtil.validateToken(token, user)){
            return(User) user;
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, null, "".getBytes(), null);
    }


}
