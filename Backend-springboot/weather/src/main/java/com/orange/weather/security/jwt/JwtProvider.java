package com.orange.weather.security.jwt;

import io.jsonwebtoken.*;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.orange.weather.security.services.UserPrinciple;

@Component
public class JwtProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Value("${orange.weather.app.jwtSecret}")
    private String jwtSecret;
 
    @Value("${orange.weather.app.jwtExpiration}")
    private int jwtExpiration;
	
    
    /**
	 * generate JWT valid token.
	 * @param	object of spring security authentication.  
	 * @return JWT token in string format.
	 * @author Ahmed Basuny 
	 */
    public String generateJwtToken(Authentication authentication) {
    	 
    	logger.info(" generateJwtToken function is called ..");
		logger.debug(" generateJwtToken function parameters are .." + authentication.toString());
    	
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
 
        return Jwts.builder()
		                .setSubject((userPrincipal.getUsername()))
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
		                .signWith(SignatureAlgorithm.HS512, jwtSecret)
		                .compact();
    }
    
    
    /**
	 * validate JWT token.
	 * @param	token in string format.  
	 * @return boolean
	 * @author Ahmed Basuny 
	 */
    public boolean validateJwtToken(String authToken) {
    	
    	logger.info(" validateJwtToken function is called ..");
		logger.debug(" validateJwtToken function parameters are .." + authToken);
    	
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature. Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token. Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token. Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token. Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty. Message: {}", e);
        }
        
        return false;
    }
    
    /**
	 * extract username fron the token
	 * @param	token in string format  
	 * @return username in string format
	 * @author Ahmed Basuny 
	 */
    public String getUserNameFromJwtToken(String token) {
    	
    	logger.info(" getUserNameFromJwtToken function is called ..");
		logger.debug(" getUserNameFromJwtToken function parameters are .." + token);
    	
        return Jwts.parser()
			                .setSigningKey(jwtSecret)
			                .parseClaimsJws(token)
			                .getBody().getSubject();
    }
}
