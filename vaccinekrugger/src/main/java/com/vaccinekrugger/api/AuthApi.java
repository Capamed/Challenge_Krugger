package com.vaccinekrugger.api;

import java.text.ParseException;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vaccinekrugger.bo.IUsersBO;
import com.vaccinekrugger.dao.impl.RoleCustomDAOImpl;
import com.vaccinekrugger.dao.impl.UsersCustomDAOImpl;
import com.vaccinekrugger.dto.JwtDTO;
import com.vaccinekrugger.dto.LoginUserDTO;
import com.vaccinekrugger.dto.RequestCreateEmployeeDTO;
import com.vaccinekrugger.dto.ResponseOk;
import com.vaccinekrugger.exceptions.BOException;
import com.vaccinekrugger.exceptions.CustomExceptionHandler;
import com.vaccinekrugger.security.jwt.JwtProvider;
import com.vaccinekrugger.utils.MessagesUtil;

@RestController
@RequestMapping("/auth")
public class AuthApi {
	@Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
	@Autowired
    private JwtProvider jwtProvider;
	@Autowired
	private IUsersBO iUsersBO;
    

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginUserDTO loginUser, BindingResult bidBindingResult,
    		@RequestHeader(value="Accept-Language", required = false) String strLanguage){
        if(bidBindingResult.hasErrors())
            return new ResponseEntity<>(MessagesUtil.getMessage("project.response.ok", MessagesUtil.validateSupportedLocale(strLanguage)), HttpStatus.BAD_REQUEST);
        try {
                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword());
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtProvider.generateToken(authentication);
                JwtDTO jwtDto = new JwtDTO(jwt);
                return new ResponseEntity<>(jwtDto, HttpStatus.OK);
        } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    
	//Create Employee
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createEmployee(
			@RequestHeader(value="Accept-Language", required = false) String strLanguage,
			@RequestBody(required = false) RequestCreateEmployeeDTO objRequestCreateEmployeeDTO,BindingResult bindingResult)throws BOException, ParseException {
		try {
			if (bindingResult.hasErrors())
	            return new ResponseEntity<>("Revise los campos e intente nuevamente", HttpStatus.BAD_REQUEST);
			HashMap<String, Integer> idUser = iUsersBO.createEmployee(objRequestCreateEmployeeDTO);
			return new ResponseEntity<>(new ResponseOk(
					MessagesUtil.getMessage("project.response.ok", MessagesUtil.validateSupportedLocale(strLanguage)),
					idUser), HttpStatus.OK);
		} catch (BOException be) {
			throw new CustomExceptionHandler(be.getTranslatedMessage(strLanguage), be.getData());
		}
	}
}
