package com.greenapp.shopcatalogmanager.service;

import com.greenapp.shopcatalogmanager.configuration.ItemNotFoundException;
import com.greenapp.shopcatalogmanager.domain.ClientRewards;
import com.greenapp.shopcatalogmanager.domain.RewardItem;
import com.greenapp.shopcatalogmanager.domain.SoldStatus;
import com.greenapp.shopcatalogmanager.dto.FilterDTO;
import com.greenapp.shopcatalogmanager.dto.PriceRangeDTO;
import com.greenapp.shopcatalogmanager.dto.RewardItemDTO;
import com.greenapp.shopcatalogmanager.repository.ClientsRepository;
import com.greenapp.shopcatalogmanager.repository.RewardRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardsService {

    private final RewardRepository rewardsRepository;
    private final ClientsRepository clientsRepository;
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
                .amount(dto.getAmount())
                .status(SoldStatus.AVAILABLE)
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

    @Transactional
    public void updateRewardStatus(Long rewardId, SoldStatus status) {
        if (status.equals(SoldStatus.SOLD)) LOG.info("Updating item :{} status to ACTIVE ", rewardId);
        else LOG.info("Updating item :{} status to SOLD", rewardId);
        rewardsRepository.findById(rewardId)
                .ifPresent(rewardItem -> {
                    rewardItem.setStatus(status);
                    rewardsRepository.save(rewardItem);
                });
    }

    @Transactional
    public ResponseEntity<HttpStatus> assign(Long rewardId, Long customerId) {
        var reward = rewardsRepository.findById(rewardId)
                .orElseThrow(ItemNotFoundException::new);

        if (reward.getAmount() <= 0) {
            throw new RuntimeException("Amount of this item is 0!");
        }
        reward.setStatus(SoldStatus.SOLD);
        reward.setAmount(reward.getAmount() - 1);
        rewardsRepository.save(reward);
        clientsRepository.save(ClientRewards
                .builder()
                .clientId(customerId)
                .rewardId(rewardId)
                .build());
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    public List<RewardItem> parseClientItems(Long clientId) {
        var clientItemsIds = clientsRepository.findAllByClientId(clientId)
                .stream()
                .map(ClientRewards::getRewardId)
                .collect(Collectors.toList());
        if (!clientItemsIds.isEmpty()) {
            return rewardsRepository.findAllByIdIn(clientItemsIds);
        }
        return Collections.emptyList();
    }

    @Transactional
    public ResponseEntity<HttpStatus> rollback(Long clientId, Long rewardId) {
        clientsRepository.deleteByClientIdAndRewardId(clientId, rewardId);
        var reward = rewardsRepository.findById(rewardId).orElseThrow(ItemNotFoundException::new);
        reward.setStatus(SoldStatus.AVAILABLE);
        reward.setAmount(reward.getAmount() + 1);
        rewardsRepository.save(reward);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Transactional
    public void removeItem(Long itemId) {
        rewardsRepository.deleteById(itemId);
    }
}
