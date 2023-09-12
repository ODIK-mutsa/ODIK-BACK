package com.micutne.odik.repository;

import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.search.SearchKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, Integer> {
    default SearchKeyword findByIdxOrThrow(int idx) {
        return findByIdx(idx)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SEARCH_KEYWORD_NOT_FOUND));
    }

    Optional<SearchKeyword> findByIdx(int idx);

    default SearchKeyword findByKeywordOrThrow(String keyword) {
        return findByKeyword(keyword)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SEARCH_KEYWORD_NOT_FOUND));
    }

    Optional<SearchKeyword> findByKeyword(String keyword);


    Boolean existsByKeyword(String keyword);

    Boolean existsByIdx(int idx);

    Page<SearchKeyword> findAllByOrderByCountDesc(Pageable pageable);
}
