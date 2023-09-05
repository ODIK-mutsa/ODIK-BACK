package com.micutne.odik.service;

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
}
