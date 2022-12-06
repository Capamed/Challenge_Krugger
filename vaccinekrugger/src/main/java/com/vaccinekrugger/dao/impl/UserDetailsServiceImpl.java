package com.vaccinekrugger.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vaccinekrugger.dto.UsersRoleDTO;
import com.vaccinekrugger.model.MainUser;
import com.vaccinekrugger.model.Users;
import com.vaccinekrugger.model.UsersRole;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    private UsersCustomDAOImpl objUsersCustomDAOImpl;
   

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        List<UsersRoleDTO> user = objUsersCustomDAOImpl.getUserByName(userName);
        if(!(user.size() > 0)) {
        	throw new UsernameNotFoundException("Bad credentials");
        }
        
        return MainUser.build(user);
    }
}
