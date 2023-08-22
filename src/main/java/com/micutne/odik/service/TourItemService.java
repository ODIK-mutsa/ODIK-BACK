package com.micutne.odik.service;

import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.tour.dto.TourItemListResponse;
import com.micutne.odik.domain.tour.dto.TourItemMapper;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.repository.TourItemRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TourItemService {
    private final TourItemRepository tourItemRepository;
    private final UserRepository userRepository;
    private final TourItemMapper tourItemMapper;



    /**
     * 특정 관광지 불러오기
     */

    public TourItemResponse readOne(Long idx) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(idx);
        return tourItemMapper.toDto(tourItem);
    }

    /**
     * 전체 관광지 전체 불러오기
     */

    public Page<TourItemListResponse> readAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return tourItemRepository.findAllBy(pageable).map(tourItemMapper::toListDto);
    }

    /**
     * 관광지 생성
     */
    @Transactional
    public TourItemResponse create(TourItemRequest request) {
        TourItem tourItem = tourItemMapper.toEntity(request);
       // tourItem.updateUser(userRepository.findByIdOrThrow(id));
       tourItem = tourItemRepository.save(tourItem);
        return tourItemMapper.toDto(tourItem);
    }

}


