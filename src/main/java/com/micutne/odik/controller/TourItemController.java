package com.micutne.odik.controller;

import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.service.TourItemService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tour/item")
public class TourItemController {
    private final TourItemService tourItemService;

    /**
     * 특정 관광지 또는 전체 관광지 불러오기
     */
    @GetMapping("")
    public Object readOneOrAll(@RequestParam(required = false) String reference_id,
                               @RequestParam(name = "no", defaultValue = "0") int pageNo,
                               @RequestParam(name = "size", defaultValue = "20") int pageSize) {
        if (reference_id != null) {
            return tourItemService.readOne(reference_id);
        } else {
            return tourItemService.readAll(pageNo, pageSize);
        }
    }

    /**
     * 관광지 생성
     */
    @PostMapping("")
    public TourItemResponse create(
            Authentication authentication,
            @RequestBody TourItemRequest request) {
        return tourItemService.create(request, authentication.getPrincipal().toString());
    }

    /**
     * 관광지 수정
     */
    @PutMapping("/{idx}")
    public TourItemResponse update(
            Authentication authentication,
            @PathVariable int idx,
            @Valid @RequestBody TourItemRequest request) {
        return tourItemService.update(idx, request, authentication.getPrincipal().toString());
    }

    /**
     * 관광지 삭제
     */
    @DeleteMapping("/{idx}")
    public void remove(
            Authentication authentication,
            @PathVariable int idx) {
        tourItemService.remove(idx, authentication.getPrincipal().toString());
    }

    /**
     * 관광지 검색 (제목)
     */
    @GetMapping("/title")
    public Page<TourItemResponse> searchByTitle(
            @RequestParam("query") String query,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo
    ) {
        return tourItemService.searchByTitle(query, pageNo);
    }

    /**
     * 관광지 검색 (타입-카테고리)
     */
    @GetMapping("/type")
    public Page<TourItemResponse> searchByType(
            @RequestParam("query") String query,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo
    ) {
        return tourItemService.searchByType(query, pageNo);
    }

}


