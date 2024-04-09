package com.itgura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponseDto {
	
	@JsonProperty("page_number")
	private int pageNumber;
	
	@JsonProperty("page_size")
	private int pageSize;
	
	@JsonProperty("total_records")
	private long totalRecords;
}
