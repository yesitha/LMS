package com.itgura.util;

public interface SearchResponseDtoBuilder<SearchResult, SearchResponse> {
	SearchResponse buildResponseDto(SearchResult searchResult);
}
