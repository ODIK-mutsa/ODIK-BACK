package com.micutne.odik.service;

import com.micutne.odik.domain.search.SearchKeyword;
import com.micutne.odik.domain.search.dto.SearchKeywordResponse;
import com.micutne.odik.domain.search.dto.SearchKeywordResultResponse;
import com.micutne.odik.repository.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchKeywordService {
    private final SearchKeywordRepository searchKeywordRepository;


    public SearchKeywordResultResponse readRank(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<SearchKeyword> result = searchKeywordRepository.findAllByOrderByCountDesc(pageable);
        return SearchKeywordResultResponse.fromEntity(result.map(SearchKeywordResponse::fromEntity), "OK");
    }


}
