package com.greenapp.shopcatalogmanager.api;

import com.greenapp.shopcatalogmanager.domain.RewardItem;
import com.greenapp.shopcatalogmanager.domain.SoldStatus;
import com.greenapp.shopcatalogmanager.dto.AssignDTO;
import com.greenapp.shopcatalogmanager.dto.FilterDTO;
import com.greenapp.shopcatalogmanager.dto.PriceRangeDTO;
import com.greenapp.shopcatalogmanager.dto.RewardItemDTO;
import com.greenapp.shopcatalogmanager.service.RewardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("shop/")
public class RewardsController {

    private final RewardsService service;

    @Async
    @GetMapping("load/all")
    public CompletableFuture<List<RewardItem>> loadShop() {
        return service.loadAllItems();
    }

    @PostMapping("create")
    public ResponseEntity<RewardItem> createNewRewardItem(@Valid @RequestBody RewardItemDTO dto) {
        return service.createRewardItem(dto);
    }

    @Async
    @GetMapping("load/item/{itemId}")
    public CompletableFuture<RewardItem> loadItemById(@PathVariable Long itemId) {
        return service.loadItemById(itemId);
    }

    @Async
    @PostMapping("load/by/date")
    public CompletableFuture<List<RewardItem>> loadItemsByFilter(@RequestBody FilterDTO date) {
        return service.filterCreatedDate(date);
    }

    @PutMapping("update/{itemId}")
    public ResponseEntity<RewardItem> updateItem(@RequestBody RewardItemDTO dto, @PathVariable Long itemId) {
        return service.update(dto, itemId);
    }

    @Async
    @GetMapping("company/{companyId}/items")
    public CompletableFuture<List<RewardItem>> loadCompanyItems(@PathVariable Long companyId) {
        return service.loadCompanyRewards(companyId);
    }

    @Async
    @PostMapping("load/price/range")
    public CompletableFuture<List<RewardItem>> filterByPriceRange(@RequestBody PriceRangeDTO dto) {
        return service.filterByPrice(dto);
    }

    @PutMapping("update/{id}/status")
    public void updateRewardStatus(@PathVariable Long id, @RequestParam SoldStatus status) {
        service.updateRewardStatus(id, status);
    }

    @DeleteMapping("remove/{rewardId}")
    public void removeItem(@PathVariable Long rewardId) {
        service.removeItem(rewardId);
    }
}
