package com.greenapp.shopcatalogmanager.service;

import com.greenapp.shopcatalogmanager.domain.Attachment;
import com.greenapp.shopcatalogmanager.domain.RewardItem;
import com.greenapp.shopcatalogmanager.dto.RewardItemDTO;
import com.greenapp.shopcatalogmanager.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RewardsService {

    private final RewardRepository rewardsRepository;

    @Async
    public CompletableFuture<List<RewardItem>> loadAllItems() {
        return CompletableFuture.completedFuture(rewardsRepository.findAll());
    }

    public ResponseEntity<RewardItem> createRewardItem(RewardItemDTO dto) {
        var now = LocalDateTime.now();
        var instance = RewardItem.builder()
                .price(dto.getPrice())
                .description(dto.getDescription())
                .createdWhen(Timestamp.valueOf(now))
                .lastUpdated(Timestamp.valueOf(now))
                .createdBy(dto.getCreatedBy())
                .title(dto.getTitle())
                .headerPhoto(dto.getContent())
                .build();
        return ResponseEntity.ok(rewardsRepository.save(instance));
    }

}
