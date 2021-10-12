package io.github.idelvane.customermanagement.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * Classe que implementa método utilitários para API.
 * 
 * @author Idelvane
 */
public class CustomerApiUtil {
	
	/**
	 * Representa o API version nos requests/responses header
	 */
	public static final String HEADER_CUSTOMER_MANAGEMENT_API_VERSION = "customer-management-api-version";
	
	/**
	 * Representa a chave da API no requests/responses header
	 */
	public static final String HEADER_API_KEY = "X-api-key";
	
	/**
	 * Representa o Limit Remaining nos requests/responses header
	 */
	public static final String HEADER_LIMIT_REMAINING = "X-Rate-Limit-Remaining";
    
	/**
	 * Representa API Rate Limit Retry After Seconds no requests/responses header
	 */
	public static final String HEADER_RETRY_AFTER = "X-Rate-Limit-Retry-After-Seconds";
	
	private CustomerApiUtil() {}
	
	
	/**
	 * Formata data no formato yyyy-MM-dd.
	 * 
	 * 
	 * @return <code>DateTimeFormatter</code> object
	 */
	public static DateTimeFormatter getDateFormater() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}
	
	/**
	 * Formata data no formato yyyy-MM-dd'T'HH:mm:ss.SSS'Z'.
	 * 
	 * @return <code>DateTimeFormatter</code> object
	 */
	public static DateTimeFormatter getDateTimeFormater() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	}
	
	/**
	 * Converte uma String em LocalDateTime.
	 * 
	 * @param dateAsString
	 * 
	 * @return <code>LocalDateTime</code> object
	 * @throws ParseException
	 */
	public static LocalDateTime getLocalDateTimeFromString(String dateAsString) throws ParseException{
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateISO8601 = inputFormat.parse(dateAsString);
        return dateISO8601.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
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
	
}
