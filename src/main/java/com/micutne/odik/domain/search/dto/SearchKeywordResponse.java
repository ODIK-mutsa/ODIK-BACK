package com.micutne.odik.domain.search.dto;

import com.micutne.odik.domain.search.SearchKeyword;
import lombok.Data;

@Data
public class SearchKeywordResponse {
    String keyword;
    double count;

    public static SearchKeywordResponse fromEntity(SearchKeyword searchKeyword) {
        SearchKeywordResponse response = new SearchKeywordResponse();
        response.keyword = searchKeyword.getKeyword();
        response.count = searchKeyword.getCount();
        return response;
    }
}
