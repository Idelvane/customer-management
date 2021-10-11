package io.github.idelvane.managecustomers.dto.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Response genérico para errors
 * 
 * @author Idelvane
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class ResponseError {
	
	@NotNull(message="Timestamp não pode ser nulo")
	private LocalDateTime timestamp;
	
	@NotNull(message="Details não pode ser nulo")
    private String details;

}