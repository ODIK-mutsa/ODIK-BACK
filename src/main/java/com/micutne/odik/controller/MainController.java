package com.micutne.odik.controller;

import com.micutne.odik.domain.etc.RecommendResponse;
import com.micutne.odik.domain.search.dto.SearchKeywordResultResponse;
import com.micutne.odik.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final RecommendService recommendService;

    @GetMapping("/tour/recommend")
    public RecommendResponse homeRecommendList(Authentication authentication) {
        String username = (authentication != null) ? authentication.getPrincipal().toString() : null;
        return recommendService.readRecommendList(username);
    }

    @GetMapping("/search/top/{limit}")
    public SearchKeywordResultResponse searchTopRank(@PathVariable int limit) {
        return recommendService.readRank(limit);
    }
}
