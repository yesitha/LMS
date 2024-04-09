package com.itgura.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaDataResponseDto {
	
	private Integer code;
	
	private String message;
}
