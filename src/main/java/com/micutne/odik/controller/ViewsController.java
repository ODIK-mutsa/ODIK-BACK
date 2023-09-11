package com.micutne.odik.controller;

import com.micutne.odik.domain.search.dto.SearchKeywordResultResponse;
import com.micutne.odik.service.SearchKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ViewsController {
    private final SearchKeywordService searchKeywordService;

    @GetMapping("/views/login")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @GetMapping("/search/top/{limit}")
    public SearchKeywordResultResponse searchTopRank(@PathVariable int limit) {
        return searchKeywordService.readRank(limit);
    }
}
