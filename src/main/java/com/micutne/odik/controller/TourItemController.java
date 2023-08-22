package com.micutne.odik.controller;

import com.micutne.odik.domain.tour.dto.TourItemListResponse;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.service.TourItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/tour/item")
public class TourItemController {
    private final TourItemService tourItemService;




    /**
     * 특정 관광지 불러오기
     */

    @GetMapping("{idx}")
    public TourItemResponse readTourItemById(@PathVariable Long idx) {
        return tourItemService.readOne(idx);
    }

    /**
     * 전체 관광지 불러오기
     */

    @GetMapping("")
    public Page<TourItemListResponse> readAllTourItem(
            @RequestParam(name = "no", defaultValue = "0") int pageNo,
            @RequestParam(name = "size", defaultValue = "20") int pageSize) {

        return tourItemService.readAll(pageNo, pageSize);
    }

    /**
     * 관광지 생성
     */

    @PostMapping("")
    public TourItemResponse createTourItem(
            //Authentication authentication,
            @ModelAttribute TourItemRequest request) {
            //@RequestParam(value = "image", required = false)MultipartFile image) {
       // log.info(request.toString());

        return tourItemService.create(request);//, image, authentication.getPrincipal().toString());
    }







}


