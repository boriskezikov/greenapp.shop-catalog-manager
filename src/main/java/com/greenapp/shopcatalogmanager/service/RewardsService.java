package com.greenapp.shopcatalogmanager.service;

import com.greenapp.shopcatalogmanager.configuration.ItemNotFoundException;
import com.greenapp.shopcatalogmanager.domain.RewardItem;
import com.greenapp.shopcatalogmanager.dto.FilterDTO;
import com.greenapp.shopcatalogmanager.dto.PriceRangeDTO;
import com.greenapp.shopcatalogmanager.dto.RewardItemDTO;
import com.greenapp.shopcatalogmanager.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RewardsService {

    private final RewardRepository rewardsRepository;

    private static final Logger LOG = LoggerFactory.getLogger(RewardsService.class.getName());

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
        LOG.info("Company {} added new reward item with title:{} and price:{}", dto.getCreatedBy(), dto.getTitle(), dto.getPrice());
        return ResponseEntity.ok(rewardsRepository.save(instance));
    }

    @Async
    public CompletableFuture<RewardItem> loadItemById(Long itemId) {
        return CompletableFuture.completedFuture(
                rewardsRepository.findById(itemId)
                        .orElseThrow(ItemNotFoundException::new));
    }

    @Async
    public CompletableFuture<List<RewardItem>> filterCreatedDate(FilterDTO dto) {
        return CompletableFuture.completedFuture(
                rewardsRepository.findAllByCreatedWhen(dto.getCreatedWhen())
                        .orElseThrow(ItemNotFoundException::new));
    }

    @Transactional
    public ResponseEntity<RewardItem> update(RewardItemDTO dto, Long id) {
        LOG.info("Updating item:{} ", id);
        return ResponseEntity.ok(rewardsRepository.findById(id)
                .map(item -> {
                    item.setLastUpdated(Timestamp.valueOf(LocalDateTime.now()));
                    item.setDescription(dto.getDescription());
                    item.setPrice(dto.getPrice());
                    item.setHeaderPhoto(dto.getContent());
                    item.setTitle(dto.getTitle());
                    return item;
                }).map(rewardsRepository::save)
                .orElseThrow(ItemNotFoundException::new));

    }

    @Async
    public CompletableFuture<List<RewardItem>> loadCompanyRewards(Long companyId) {
        return CompletableFuture
                .completedFuture(rewardsRepository
                        .findAllByCreatedBy(companyId));
    }

    @Async
    public CompletableFuture<List<RewardItem>> filterByPrice(PriceRangeDTO dto) {
        return CompletableFuture
                .completedFuture(rewardsRepository
                        .findAllByPrice(dto.getFrom(), dto.getTo()));
    }
}
