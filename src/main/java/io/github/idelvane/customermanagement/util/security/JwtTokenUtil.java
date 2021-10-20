package io.github.idelvane.customermanagement.util.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Classe que implementa métodos utilitário para o JwtToken
 */
@Component
public class JwtTokenUtil {
	
	private static final String CLAIM_KEY_USERNAME = "sub";
	
	private static final String CLAIM_KEY_ROLE = "role";
	
	private static final String CLAIM_KEY_CREATED = "created";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	/**
	 * Método que retorna o nome do usuario (email) a partir de um token
	 * 
	 * @param token
	 * @return String - username
	 */
	public String getUsernameFromToken(String token) {
		
		String username;
		
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		
		return username;
	}

	/**
	 * Metodo que retorna da data de expiração de um token
	 * 
	 * 
	 * @param token
	 * @return Date - expirationDate
	 */
	public Date getExpirationDateFromToken(String token) {
		
		Date expiration;
		
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		
		return expiration;
	}

	/**
	 * Metodo que indica se um token é válido
	 * 
	 * @param token
	 * @return boolean
	 */
	public boolean validToken(String token) {
		return !expiredToken(token);
	}

	/**
	 * Metodo que retorna um token para um usuário
	 * 
	 * 
	 * @param userDetails
	 * @return String - token
	 */
	public String getToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));

		return generateToken(claims);
	}
	

	private Claims getClaimsFromToken(String token) throws AuthenticationException {
		
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		
		return claims;
	}

	/**
	 * Método que retorna a data de expiração de um token
	 * 
	 * @return Date - expirationDate 
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
	
	/**
	 * Método que retorna se um token está expirado ou não
	 * 
	 * @param token
	 * @return boolean
	 */
	private boolean expiredToken(String token) {
		Date expirationDate = this.getExpirationDateFromToken(token);
		if (expirationDate == null) {
			return false;
		}
		return expirationDate.before(new Date());
	}
	
	/**
	 * Método que gera um token valido
	 * 
	 * @param claims
	 * @return String - token
	 */
	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
