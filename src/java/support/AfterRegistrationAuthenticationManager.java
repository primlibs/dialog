/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package support;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author bezdatiuzer
 */

  
 
public class AfterRegistrationAuthenticationManager implements AuthenticationManager {
    
  static final List<GrantedAuthority> AUTHORITIES = new ArrayList();
 
  static {
    AUTHORITIES.add(new SimpleGrantedAuthority("admin"));
  }
 
  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    
      return new UsernamePasswordAuthenticationToken(auth.getName(),auth.getCredentials(), AUTHORITIES);
  }
}

