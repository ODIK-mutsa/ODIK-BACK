package com.micutne.odik.service;

import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.BusinessException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.tour.dto.TourItemListResponse;
import com.micutne.odik.domain.tour.dto.TourItemMapper;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.domain.user.User;
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
     * 관광지 저장
     */
    @Transactional
    public TourItemResponse create(TourItemRequest request){ //}, String id) {
       TourItem tourItem = tourItemMapper.toEntity(request);
       //tourItem.updateUser(userRepository.findByIdOrThrow(id));
       tourItem = tourItemRepository.save(tourItem);
        return tourItemMapper.toDto(tourItem);
    }

    /**
     * 관광지 수정
     */
    @Transactional
    public TourItemResponse update(Long idx, TourItemRequest request) { //}, String id) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(idx);
        //User user = userRepository.findByIdOrThrow(id);
        // checkAuth(item, user);
        tourItem.updateTourItem(request);
        return tourItemMapper.toDto(tourItem);

    }

    /**
     * 관광지 삭제
     */
    public void remove(Long idx) { //}, String id) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(idx);
        // User user = userRepository.findByIdOrThrow(id);
        // checkAuth(item, user);
        try {
            tourItemRepository.delete(tourItem);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.TOUR_ITEM_DELETE_FAIL);
        }
    }

    /**
     * 사용자 확인(추후 수정 및 추가)
     */
    public void checkAuth(TourItem tourItem, User user) {
        if (!tourItem.getUser().equals(user)) throw new AuthException(ErrorCode.USER_NOT_FOUND);
    }

}


