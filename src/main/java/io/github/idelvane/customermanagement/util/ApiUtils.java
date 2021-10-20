package io.github.idelvane.customermanagement.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.github.idelvane.customermanagement.model.Customer;


/**
 * Classe que implementa método utilitários para API.
 * 
 * @author Idelvane
 */
public class ApiUtils {
	
	/**
	 * Representa o API version nos requests/responses header
	 */
	public static final String API_VERSION = "customer-management-api-version";
	
	/**
	 * Representa a chave da API no requests/responses header
	 */
	public static final String HEADER_API_KEY = "X-api-key";
	
	
	private ApiUtils() {}
	
	/**
	 * Converte uma String em LocalDateTime.
	 * 
	 * @param dateString
	 * 
	 * @return <code>LocalDateTime</code> object
	 * @throws ParseException
	 */
	public static LocalDateTime convertStringToLocalDateTime(String dateString) throws ParseException{
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = inputFormat.parse(dateString);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	/**
	 * Converte um LocalDate em LocalDateTime 
	 * 
	 * 
	 * @param date
	 * @return <code>LocalDateTime</code> object
	 */
	public static LocalDateTime convertLocalDateToLocalDateTime(LocalDate date) {
		return date.atTime(0, 0, 0);
	}

	/**
	 * Returna a idade de um {@link Customer} a partir da data de nascimento 
	 * @param birthDate
	 * @return
	 */
	public static int calculateAge(LocalDateTime birthDate) {
		final LocalDate now = LocalDate.now();
	    final Period period = Period.between(birthDate.toLocalDate(), now);
	    return period.getYears();
	}
	
	public static String getHash(String password) {
		
		if(password == null) {
			return null;
		}
		
		if(isEncrypted(password)) {
			return password;
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
	
	/**
	 * Method that check if the password is already encoding.
	 * 
	 * @param password
	 * @return boolean
	 */
	public static boolean isEncrypted(String password) {
		return password.startsWith("$2a$");
	}
	
	/**
	 * Method that decode the raw password.
	 * 
	 * @param password
	 * @param encrypted
	 * @return String password decoded
	 */
	public static String decode(String password, String encrypted) throws Exception {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		boolean isPasswordMatches = bcrypt.matches(password, encrypted);
		
		if(!isPasswordMatches)
			throw new Exception("Password does not match.");
		
		return password;
	}
}
