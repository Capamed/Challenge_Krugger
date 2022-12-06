package com.vaccinekrugger.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vaccinekrugger.dto.UsersRoleDTO;

public class MainUser implements UserDetails {
    private String userName;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public MainUser(String userName, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }

    public static MainUser build(List<UsersRoleDTO> userRole){
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	for (UsersRoleDTO usersRoleDTO : userRole) {
    		authorities.add(new SimpleGrantedAuthority(usersRoleDTO.getRole()));
		}
    	return new MainUser(userRole.get(0).getUsername(), userRole.get(0).getPassword(), authorities);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public String getEmail() {
        return email;
    }
    
}