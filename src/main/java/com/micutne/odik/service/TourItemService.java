package com.micutne.odik.service;

import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.BusinessException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.imageTourItem.ImageTourItem;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.tour.dto.TourItemResultListResponse;
import com.micutne.odik.domain.tour.dto.TourItemListResponse;
import com.micutne.odik.domain.tour.dto.TourItemMapper;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.ImageTourItemRepository;
import com.micutne.odik.repository.TourItemRepository;
import com.micutne.odik.repository.UserRepository;
import com.micutne.odik.utils.file.Extensions;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TourItemService {
    private static final String[] ARTICLE_FILE_URL = Extensions.IMAGE.getExtensions();
    private final TourItemRepository tourItemRepository;
    private final UserRepository userRepository;
    private final TourItemMapper tourItemMapper;
    private final ImageTourItemRepository imageTourItemRepository;


    /**
     * 특정 관광지 불러오기
     */

    public TourItemResponse readOne(String reference_id) {
        TourItem tourItem = tourItemRepository.findByReferenceIdGoogle(reference_id);
        return TourItemResponse.fromEntity(tourItemRepository.findByReferenceIdGoogle(reference_id));
    }

    /**
     * 관광지 저장
     */
    @Transactional
    public TourItemResponse create(TourItemRequest request, String id) {
        if (tourItemRepository.existsByReferenceIdGoogle(request.getReference_id_google()))
            return new TourItemResponse(String.valueOf(ErrorCode.TOUR_ITEM_ALREADY_EXIST));

        request.setUser(userRepository.findByIdOrThrow(id));
        TourItem tourItem = tourItemMapper.toEntity(request);
        tourItem = tourItemRepository.save(tourItem);

        List<ImageTourItem> imageTourItems = new ArrayList<>();

        if (request.getImages_google() != null) {
            for (String imageUrl : request.getImages_google()) {
                // Url 또는 파일 추가 로직 ( 파일 추가부분 수정 필요 )
                boolean saveUrl = imageUrl.startsWith("http://") || imageUrl.startsWith("https://");
                String saveFile = "saveFile";
                ImageTourItem imageTourItem = ImageTourItem.builder()
                        .tour_item_idx(tourItem)
                        .url(saveUrl ? imageUrl : saveFile)
                        .build();
                imageTourItems.add(imageTourItem);
            }
        }

        imageTourItems = imageTourItemRepository.saveAll(imageTourItems);

        TourItemResponse response = TourItemResponse.fromEntity(tourItem, imageTourItems);
        response.setImages_google(imageTourItems.stream().map(ImageTourItem::getImagesGoogle).toList());
        return response;
    }

    /**
     * 관광지 수정
     */
    @Transactional
    public TourItemResponse update(int idx, TourItemRequest request, String id) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(idx);
        User user = userRepository.findByIdOrThrow(id);
        checkAuth(tourItem, user);
        tourItem.updateTourItem(request);
        return TourItemResponse.fromEntity(tourItem);

    }

    /**
     * 관광지 삭제
     */
    public void remove(int idx, String id) {
        TourItem tourItem = tourItemRepository.findByIdOrThrow(idx);
        User user = userRepository.findByIdOrThrow(id);
        checkAuth(tourItem, user);
        try {
            tourItemRepository.delete(tourItem);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.TOUR_ITEM_DELETE_FAIL);
        }
        log.info("delete a tour item");
    }

    /**
     * 관광지 검색 또는 전체 불러오기
     */
    public TourItemResultListResponse searchAll(String title, String orderBy, int pageNo, int pageSize) {
        title = URLDecoder.decode(title, StandardCharsets.UTF_8);
        log.info("title" + title);
        String[] keywords = title.split(" ");
        Specification<TourItem> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keywords.length > 0) {
                Predicate[] keywordPredicates = new Predicate[keywords.length];
                for (int i = 0; i < keywords.length; i++) {
                    keywordPredicates[i] = criteriaBuilder.like(root.get("title"), "%" + keywords[i] + "%");
                }
                predicates.add(criteriaBuilder.or(keywordPredicates));
            }
            predicates.add(criteriaBuilder.equal(root.get("state"), "public"));

            log.info("builder" + String.valueOf(criteriaBuilder.and(predicates.toArray(new Predicate[0]))));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable;
        String[] sortData = findField(orderBy);

        if (sortData[1].equals("desc")) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortData[0]));
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortData[0]));
        }
        Page<TourItem> tourItems = tourItemRepository.findAll(spec, pageable);

        log.info("result" + TourItemResultListResponse.fromEntity(tourItems.map(TourItemResponse::fromEntity).toString()).toString());
        return TourItemResultListResponse.fromEntity(tourItems.map(TourItemResponse::fromEntity), "OK");
    }


    private String[] findField(String orderBy) {
        switch (orderBy) {
            case "recent" -> {
                return new String[]{"dateCreate", "desc"};
            }
            case "like" -> {
                return new String[]{"countLike", "asc"};
            }
            default -> {
                return new String[]{"dateCreate", "desc"};
            }
        }
    }

    /**
     * 사용자 확인(추후 수정 및 추가)
     */
    public void checkAuth(TourItem tourItem, User user) {
        if (!tourItem.getUser().equals(user)) throw new AuthException(ErrorCode.USER_NOT_FOUND);
    }


}