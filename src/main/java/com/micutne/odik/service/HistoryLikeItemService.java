package com.micutne.odik.service;

import com.micutne.odik.domain.like.HistoryLikeTourItem;
import com.micutne.odik.domain.like.dto.ItemLikeRequest;
import com.micutne.odik.domain.like.dto.LikeResponse;
import com.micutne.odik.domain.tour.TourItem;
import com.micutne.odik.domain.user.User;
import com.micutne.odik.repository.HistoryLikeTourItemRepository;
import com.micutne.odik.repository.TourItemRepository;
import com.micutne.odik.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HistoryLikeItemService {
    private final TourItemRepository tourItemRepository;
    private final UserRepository userRepository;
    private final HistoryLikeTourItemRepository historyLikeTourItemRepository;

    public LikeResponse read(int itemId, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (tourItemRepository.existsById(itemId)) {
            TourItem tourItem = tourItemRepository.findByIdOrThrow(itemId);

            if (!historyLikeTourItemRepository.existsByTourItemAndUser(tourItem, user))
                return LikeResponse.toDto(false, "OK");

            return LikeResponse.toDto(true, "OK");
        }
        return LikeResponse.toDto("COURSE_NOT_EXIST");
    }

    public LikeResponse update(int itemId, ItemLikeRequest request, String username) {
        User user = userRepository.findByIdOrThrow(username);
        if (tourItemRepository.existsById(itemId)) {
            TourItem tourItem = tourItemRepository.findByIdOrThrow(itemId);

            //true : 생성
            if (request.isLike()) {
                //이미 있는 경우
                if (historyLikeTourItemRepository.existsByTourItemAndUser(tourItem, user))
                    return LikeResponse.toDto(true, "OK");

                request.setUser(user);
                request.setTourItem(tourItem);
                historyLikeTourItemRepository.save(HistoryLikeTourItem.fromDto(request));
                return LikeResponse.toDto(true, "OK");
            }
            //false : 제거
            //이미 없는 경우
            if (!historyLikeTourItemRepository.existsByTourItemAndUser(tourItem, user))
                return LikeResponse.toDto(false, "OK");

            HistoryLikeTourItem entity = historyLikeTourItemRepository.findByTourItemAndUserOrThrow(tourItem, user);
            historyLikeTourItemRepository.delete(entity);

            return LikeResponse.toDto(false, "OK");
        }
        return LikeResponse.toDto("COURSE_NOT_EXIST");
    }
}
