package com.micutne.odik.service;

import com.micutne.odik.domain.etc.RecommendResponse;
import com.micutne.odik.domain.like.HistoryLikeTourCourse;
import com.micutne.odik.domain.search.SearchKeyword;
import com.micutne.odik.domain.search.dto.SearchKeywordResponse;
import com.micutne.odik.domain.search.dto.SearchKeywordResultResponse;
import com.micutne.odik.domain.tour.dto.course.TourCourseItemResponse;
import com.micutne.odik.domain.tour.dto.course.TourCourseResponse;
import com.micutne.odik.domain.tour.dto.item.TourItemResponse;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.*;
import com.micutne.odik.utils.redis.RedisResponse;
import com.micutne.odik.utils.redis.SearchRedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendService {
    private final SearchKeywordRepository searchKeywordRepository;
    private final UserRepository userRepository;
    private final TourCourseRepository tourCourseRepository;
    private final TourCourseService tourCourseService;
    private final TourItemRepository tourItemRepository;
    private final HistoryLikeTourCourseRepository historyLikeTourCourseRepository;

    public SearchKeywordResultResponse readRank(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<SearchKeyword> result = searchKeywordRepository.findAllByOrderByCountDesc(pageable);
        return SearchKeywordResultResponse.fromEntity(result.map(SearchKeywordResponse::fromEntity), "OK");
    }


    public RecommendResponse readRecommendList(String username) {
        RecommendResponse response = new RecommendResponse();
        Pageable pageable = PageRequest.of(0, 10);
        if (username != null && userRepository.existsById(username)) {
            User user = userRepository.findByIdOrThrow(username);
            //내 장바구니 관광지
            List<TourItemResponse> userItems = tourCourseService
                    .readMyCourse(username).getTour_course().getTour_items()
                    .stream().map(TourCourseItemResponse::getTour_item).toList();
            //내가 만든 코스
            List<TourCourseResponse> userCourses = tourCourseRepository
                    .findListByUserCourse(user, pageable)
                    .stream().map(TourCourseResponse::fromEntity).toList();
            //내가 좋아요 누른 코스
            List<TourCourseResponse> userLikeCourses = historyLikeTourCourseRepository.findAllByUser(user, pageable)
                    .stream().map(HistoryLikeTourCourse::getTourCourse)
                    .map(TourCourseResponse::fromEntity).toList();
            response.setRecommend(userItems, userCourses, userLikeCourses);
        }
        // 최신순 코스
        List<TourCourseResponse> latestCourses = tourCourseRepository.findAllByOrderByIdxDesc(pageable)
                .stream().map(TourCourseResponse::fromEntity).toList();
        // 좋아요 많은 코스
        List<TourCourseResponse> likeCourses = tourCourseRepository.findAllByOrderByCountLikeDesc(pageable)
                .stream().map(TourCourseResponse::fromEntity).toList();
        // 좋아요 많은 관광지
        List<TourItemResponse> likeItems = tourItemRepository.findAllByOrderByCountLikeDesc(pageable)
                .stream().map(TourItemResponse::fromEntity).toList();
        response.setLike(likeItems, likeCourses, latestCourses);
        // 실시간 10 검색어
        List<String> readTop10Keyword = SearchRedisUtils.readTopSearchKeywords(10).stream().map(RedisResponse::getKeyword).map(Object::toString).toList();
        response.setKeyword(readTop10Keyword);

        return response;
    }
}
