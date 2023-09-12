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
import com.micutne.odik.utils.file.FileResultResponse;
import com.micutne.odik.utils.file.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private final TourCourseRepository tourCourseRepository;
    private final TourItemRepository tourItemRepository;
    private final ReviewTourCourseRepository reviewTourCourseRepository;
    private final ImageReviewTourCourseRepository imageReviewTourCourseRepository;
    private final ReviewTourItemRepository reviewTourItemRepository;
    private final ImageReviewTourItemRepository imageReviewTourItemRepository;
    private final UserRepository userRepository;

    @Transactional
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

    private FileResultResponse saveReviewItem(FileRequest request, MultipartFile[] images, String username) {
        String category = request.getCategory();
        int idx = request.getIdx();
        if (reviewTourItemRepository.existsByIdx(idx)) {
            ReviewTourItem reviewTourItem = reviewTourItemRepository.findByIdOrThrow(idx);
            User user = userRepository.findByIdOrThrow(username);
            List<String> result = new ArrayList<>();
            if (reviewTourItem.getUser().equals(user)) {
                if (images[0].getSize() == 0) {
                    List<String> temps = reviewTourItem.getReviewImage().stream().map(ImageReviewTourItem::getUrl).toList();

                    return FileResultResponse.toDto("OK", result);
                }
                return FileResultResponse.toDto("OK", result);
            }
            return FileResultResponse.toDto("AUTH_FAIL");
        }
        return FileResultResponse.toDto("ENTITY_NOT_EXIST");
    }

    private FileResultResponse saveReviewCourse(FileRequest request, MultipartFile[] images, String username) {
        String category = request.getCategory();
        int idx = request.getIdx();
        if (reviewTourCourseRepository.existsByIdx(idx)) {
            ReviewTourCourse reviewTourCourse = reviewTourCourseRepository.findByIdxOrThrow(idx);
            User user = userRepository.findByIdOrThrow(username);
            List<String> result = new ArrayList<>();
            if (reviewTourCourse.getUser().equals(user)) {
                if (images[0].getSize() == 0) {
                    List<String> temps = reviewTourCourse.getReviewImage().stream().map(ImageReviewTourCourse::getUrl).toList();
                    return FileResultResponse.toDto("OK", result);
                }
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
                    return FileResultResponse.toDto("OK", result);
                }
                return FileResultResponse.toDto("OK", result);
            }
            return FileResultResponse.toDto("AUTH_FAIL");
        }
        return FileResultResponse.toDto("ENTITY_NOT_EXIST");
    }

    @Transactional
    public FileResultResponse saveTourCourse(FileRequest request, MultipartFile[] images, String username) {
        String category = request.getCategory();
        int idx = request.getIdx();
        if (tourCourseRepository.existsByIdx(idx)) {
            TourCourse tourCourse = tourCourseRepository.findByIdxOrThrow(idx);
            User user = userRepository.findByIdOrThrow(username);

            if (tourCourse.getUserIdx().equals(user)) {
                if (images[0].getSize() == 0) {
                    removeFiles(tourCourse.getImage_cover(), category);
                    tourCourse.updateImage(null);
                    List<String> result = new ArrayList<>();
                    result.add(tourCourse.getImage_cover());
                    return FileResultResponse.toDto("OK", result);
                }
                String cover = ImageUtils.saveFile(images[0], category).getFileName();
                removeFiles(tourCourse.getImage_cover(), category);
                tourCourse.updateImage(cover);
                List<String> result = new ArrayList<>();
                result.add(tourCourse.getImage_cover());
                return FileResultResponse.toDto("OK", result);
            }
            return FileResultResponse.toDto("AUTH_FAIL");
        }
        return FileResultResponse.toDto("ENTITY_NOT_EXIST");
    }

    private void removeFiles(List<String> urls, String category) {
        for (String url : urls) {
            ImageUtils.removeFile(url, category);
        }
    }

    private void removeFiles(String url, String category) {
        ImageUtils.removeFile(url, category);
    }
}
