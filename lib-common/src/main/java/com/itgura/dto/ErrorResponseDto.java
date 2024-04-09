package com.itgura.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
	
	private String status;
	
	private String source;
	
	private String title;
	
	private String code;
	
	private String detail;
}
