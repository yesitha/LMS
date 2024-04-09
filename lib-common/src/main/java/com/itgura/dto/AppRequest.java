package com.itgura.dto;


import com.itgura.util.Request;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class AppRequest<T> extends Request{
	@Valid 
	private T data;

	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
