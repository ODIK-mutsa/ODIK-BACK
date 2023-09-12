package com.micutne.odik.service;

import com.micutne.odik.domain.images.ImageReviewTourCourse;
import com.micutne.odik.domain.images.ImageReviewTourItem;
import com.micutne.odik.domain.review.ReviewTourCourse;
import com.micutne.odik.domain.review.ReviewTourItem;
import com.micutne.odik.domain.tour.TourCourse;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.*;
import com.micutne.odik.utils.file.FileRequest;
import com.micutne.odik.utils.file.FileResponse;
import com.micutne.odik.utils.file.FileResultResponse;
import com.micutne.odik.utils.file.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FileService {
    private final TourCourseRepository tourCourseRepository;
    private final TourItemRepository tourItemRepository;
    private final ReviewTourCourseRepository reviewTourCourseRepository;
    private final ImageReviewTourCourseRepository imageReviewTourCourseRepository;
    private final ReviewTourItemRepository reviewTourItemRepository;
    private final ImageReviewTourItemRepository imageReviewTourItemRepository;
    private final UserRepository userRepository;


    public FileResultResponse saveFile(FileRequest request, MultipartFile[] images, String username) {
        FileResultResponse response;
        String category = request.getCategory();
        switch (category) {
            case "tour_course" -> response = saveTourCourse(request, images, username);
            case "tour_item" -> response = saveTourItem(request, images, username);
            case "review_tour_course" -> response = saveReviewCourse(request, images, username);
            case "review_tour_item" -> response = saveReviewItem(request, images, username);
            default -> response = FileResultResponse.toDto("CATEGORY_NOT_EXIST");
        }
        return response;
    }


    public FileResultResponse saveReviewItem(FileRequest request, MultipartFile[] images, String username) {
        String category = request.getCategory();
        int idx = request.getIdx();
        if (reviewTourItemRepository.existsByIdx(idx)) {
            ReviewTourItem reviewTourItem = reviewTourItemRepository.findByIdOrThrow(idx);
            User user = userRepository.findByIdOrThrow(username);
            List<String> result = new ArrayList<>();
            if (reviewTourItem.getUser().equals(user)) {
                //이전 이미지 삭제
                List<ImageReviewTourItem> reviewTemps = reviewTourItem.getReviewImage();
                removeFiles(reviewTemps.stream().map(ImageReviewTourItem::getUrl).toList());
                imageReviewTourItemRepository.deleteAll(reviewTemps);
                reviewTourItem.setReviewImage(Collections.emptyList());
                if (images[0].getSize() == 0) {
                    return FileResultResponse.toDto("OK", result);
                }
                //새로운 이미지 저장
                List<String> saveImages = ImageUtils.saveFiles(images, category).stream().map(FileResponse::getFileName).toList();
                List<ImageReviewTourItem> entities = saveImages.stream().map(image -> ImageReviewTourItem.toEntity(reviewTourItem, image)).toList();
                imageReviewTourItemRepository.saveAll(entities);
                result = saveImages;
                return FileResultResponse.toDto("OK", result);
            }
            return FileResultResponse.toDto("AUTH_FAIL");
        }
        return FileResultResponse.toDto("ENTITY_NOT_EXIST");
    }


    public FileResultResponse saveReviewCourse(FileRequest request, MultipartFile[] images, String username) {
        String category = request.getCategory();
        int idx = request.getIdx();
        if (reviewTourCourseRepository.existsByIdx(idx)) {
            ReviewTourCourse reviewTourCourse = reviewTourCourseRepository.findByIdxOrThrow(idx);
            User user = userRepository.findByIdOrThrow(username);
            List<String> result = new ArrayList<>();
            if (reviewTourCourse.getUser().equals(user)) {
                //이전 이미지 삭제
                List<ImageReviewTourCourse> reviewTemps = reviewTourCourse.getReviewImage();
                imageReviewTourCourseRepository.deleteAll(reviewTourCourse.getReviewImage());
                removeFiles(reviewTemps.stream().map(ImageReviewTourCourse::getUrl).toList());
                reviewTourCourse.setReviewImage(Collections.emptyList());
                if (images[0].getSize() == 0) {
                    return FileResultResponse.toDto("OK", result);
                }
                List<String> saveImages = ImageUtils.saveFiles(images, category).stream().map(FileResponse::getFileName).toList();
                List<ImageReviewTourCourse> entities = saveImages.stream().map(image -> ImageReviewTourCourse.toEntity(reviewTourCourse, image)).toList();
                imageReviewTourCourseRepository.saveAll(entities);
                result = saveImages;
                return FileResultResponse.toDto("OK", result);
            }
            return FileResultResponse.toDto("AUTH_FAIL");
        }
        return FileResultResponse.toDto("ENTITY_NOT_EXIST");
    }

    private FileResultResponse saveTourItem(FileRequest request, MultipartFile[] images, String username) {
        String category = request.getCategory();
        int idx = request.getIdx();
        if (tourItemRepository.existsByIdx(idx)) {
            TourItem tourItem = tourItemRepository.findByIdOrThrow(idx);
            User user = userRepository.findByIdOrThrow(username);
            List<String> result = new ArrayList<>();
            if (tourItem.getUser().equals(user)) {
                if (images[0].getSize() == 0) {
                    return FileResultResponse.toDto("OK");
                }
                return FileResultResponse.toDto("OK", result);
            }
            return FileResultResponse.toDto("AUTH_FAIL");
        }
        return FileResultResponse.toDto("ENTITY_NOT_EXIST");
    }


    public FileResultResponse saveTourCourse(FileRequest request, MultipartFile[] images, String username) {
        String category = request.getCategory();
        int idx = request.getIdx();
        if (tourCourseRepository.existsByIdx(idx)) {
            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(idx);
            User user = userRepository.findByIdOrThrow(username);
            List<String> result = new ArrayList<>();
            if (tourCourse.getUserIdx().equals(user)) {
                if (tourCourse.getImage_cover() != null) removeFiles(tourCourse.getImage_cover());
                if (images[0].getSize() == 0) {
                    tourCourse.updateImage(null);
                    return FileResultResponse.toDto("OK", result);
                }
                String cover = ImageUtils.saveFile(images[0], category).getFileName();
                tourCourse.updateImage(cover);
                result.add(tourCourse.getImage_cover());
                return FileResultResponse.toDto("OK", result);
            }
            return FileResultResponse.toDto("AUTH_FAIL");
        }
        return FileResultResponse.toDto("ENTITY_NOT_EXIST");
    }


    public void removeFiles(List<String> urls) {
        for (String url : urls) {
            ImageUtils.removeFile(url);
        }
    }

    public void removeFiles(String url) {
        ImageUtils.removeFile(url);
    }
}
