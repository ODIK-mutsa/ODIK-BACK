package com.micutne.odik.controller;

import com.micutne.odik.domain.tour.dto.TourItemListResponse;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.service.TourItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tour/item")
public class TourItemController {
    private final TourItemService tourItemService;

    // 구현해야하는 기능:



    /**
     * 특정 관광지 불러오기
     */

    @GetMapping("{idx}")
    public TourItemResponse readOne(@PathVariable Long idx) {
        return tourItemService.readOne(idx);
    }

    /**
     * 전체 관광지 불러오기
     */

    @GetMapping("")
    public Page<TourItemListResponse> readAll(
            @RequestParam(name = "no", defaultValue = "0") int pageNo,
            @RequestParam(name = "size", defaultValue = "20") int pageSize) {

        return tourItemService.readAll(pageNo, pageSize);
    }

    /**
     * 관광지 생성
     */

    @PostMapping("")
    public TourItemResponse create(
            //Authentication authentication,
            @ModelAttribute TourItemRequest request) {
            //@RequestParam(value = "image", required = false)MultipartFile image) {
       // log.info(request.toString());

        return tourItemService.create(request);//, authentication.getName());//, image, authentication.getPrincipal().toString());
    }

    @PutMapping("/{idx}")
    public TourItemResponse update(
            // Authentication authentication,
            @PathVariable Long idx,
            @Valid @ModelAttribute TourItemRequest request) {
        return tourItemService.update(idx, request); //, id);
    }

    @DeleteMapping("/{idx}")
    public void remove (
            // Authentication authentication,
            @PathVariable Long idx) {
        tourItemService.remove(idx); //, authentication.getPrincipal().toString());
    }

}


