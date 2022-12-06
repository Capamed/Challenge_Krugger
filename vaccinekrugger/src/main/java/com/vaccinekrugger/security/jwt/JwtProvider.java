package com.vaccinekrugger.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;



@Component
public class JwtProvider {
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	//Key to verify token
	@Value("${jwt.secret}")
	private String secret;
	
	//Time expiration
	@Value("${jwt.expiration}")
	private int expiration;
	
	 public String generateToken(Authentication authentication){
	      
	        UserDetails mainUser = (UserDetails) authentication.getPrincipal();
	        logger.error(mainUser.getUsername());
	        return Jwts.builder().setSubject(mainUser.getUsername())
	        .setIssuedAt(new Date())
	        .setExpiration(new Date(new Date().getTime() + expiration *1000))
	        .signWith(SignatureAlgorithm.HS512, secret)
	        .compact();
	    }
	    //We create a function that allows us to obtain the user name with the token
	    public String getUserNameFromToken(String token){
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	    }
	
	    //We create a function that allows us to validate our token with the secret signature
	    //We control any error that may exist with the token
	    public boolean validateToken(String token){
	        try {
	            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
	            return true;
	        }catch (MalformedJwtException e){
	            logger.error("token bad build");
	        }catch (UnsupportedJwtException e){
	            logger.error("token no supported");
	        }catch (ExpiredJwtException e){
	            logger.error("token expirated");
	        }catch (IllegalArgumentException e){
	            logger.error("token empty");
	        }catch (SignatureException e){
	            logger.error("fail in the sign");
	        }
	        return false;
	    }
}
