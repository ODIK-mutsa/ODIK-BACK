package com.micutne.odik.service;

import com.micutne.odik.common.exception.AuthException;
import com.micutne.odik.common.exception.BusinessException;
import com.micutne.odik.common.exception.EntityNotFoundException;
import com.micutne.odik.common.exception.ErrorCode;
import com.micutne.odik.domain.imageTourItem.ImageTourItem;
//import com.micutne.odik.domain.imageTourItem.dto.ImageTourItemMapper;
//import com.micutne.odik.domain.imageTourItem.dto.ImageTourItemRequest;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.tour.dto.TourItemListResponse;
import com.micutne.odik.domain.tour.dto.TourItemMapper;
import com.micutne.odik.domain.tour.dto.TourItemRequest;
import com.micutne.odik.domain.tour.dto.TourItemResponse;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.ImageTourItemRepository;
import com.micutne.odik.repository.TourItemRepository;
import com.micutne.odik.repository.UserRepository;
import com.micutne.odik.utils.file.Extensions;
//import com.micutne.odik.utils.file.FileResponse;
//import com.micutne.odik.utils.file.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
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
   // private final ImageTourItemMapper imageTourItemMapper;
    //private final TourItemResponse tourItemResponse;


    /**
     * 특정 관광지 불러오기
     */

    public TourItemResponse readOne(String reference_id) {
        //TourItem tourItem = tourItemRepository.findByIdOrThrow(idx);
        //if (tourItemRepository.existsByReferenceIdGoogle(new TourItem().getReferenceIdGoogle())) {
            TourItem tourItem = tourItemRepository.findByReferenceIdGoogle(reference_id);
          //  return tourItemMapper.toDto(tourItem);
            return TourItemResponse.fromEntity(tourItemRepository.findByReferenceIdGoogle(reference_id));
        }
        //else throw new EntityNotFoundException(ErrorCode.TOUR_ITEM_NOT_FOUND);
    //}

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
    public TourItemResponse create(TourItemRequest request, String id) {
        //if (new TourItem().getReferenceIdGoogle() != (request.getReference_id_google())) {
        if (!tourItemRepository.existsByReferenceIdGoogle(new TourItem().getReferenceIdGoogle())) {
            //request.updateUser(userRepository.findByIdOrThrow(id));
            request.setUser(userRepository.findByIdOrThrow(id));
            //userRepository.findByIdOrThrow(id);
            //request.getUser(userRepository.findByIdOrThrow(id));
            TourItem tourItem = tourItemMapper.toEntity(request);
            tourItem = tourItemRepository.save(tourItem);

            /*
            TourItem tourItem = tourItemMapper.toEntity(request);
            tourItem.updateUser(userRepository.findByIdOrThrow(id));
            tourItem = tourItemRepository.save(tourItem);
            // updateState 메소드를 통해 카트에 담을 경우 cart, 코스의 일부로 저장되는 경우 course로 사용가능?
            tourItem.updateState("cart");
            //return tourItemMapper.toDto(tourItem);
             */

            List<ImageTourItem> imageTourItems = new ArrayList<>();
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
            imageTourItemRepository.saveAll(imageTourItems);

            return TourItemResponse.fromEntity(tourItem);
            //return new TourItemResponse("OK");
        } else
            //return TourItemResponse.alreadyExist("ALREADY_EXIST");
        //return new TourItemResponse(ErrorCode.TOUR_ITEM_ALREADY_EXIST);
        //return new TourItemResponse(ErrorCode.TOUR_ITEM_ALREADY_EXIST);
        return new TourItemResponse();
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
        //return tourItemMapper.toDto(tourItem);
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
    }

    /**
     * 사용자 확인(추후 수정 및 추가)
     */
    public void checkAuth(TourItem tourItem, User user) {
        if (!tourItem.getUser().equals(user)) throw new AuthException(ErrorCode.USER_NOT_FOUND);
    }


    // 이미지 생성
    /*
    private List<ImageTourItem> saveImage(MultipartFile[] images, TourItem tourItem) {
        List<FileResponse> responses = Arrays.stream(images)
                .map(file -> ImageUtils.saveFiles(file, ARTICLE_FILE_URL, file.getOriginalFilename().split("\\.")[0] ))
                .toList();

        for (FileResponse temp : responses) {
            temp.setTourItem(tourItem)
        }

    }

     */

    // delete
    /*
    private void removeImage(TourItem tourItem) {
        List<ImageTourItemRequest> images = tourItem.get
    }

     */
}