package com.greenapp.shopcatalogmanager.api;

import com.greenapp.shopcatalogmanager.domain.RewardItem;
import com.greenapp.shopcatalogmanager.service.RewardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("shop/client/")
public class ClientController {

    private final RewardsService service;

    @PostMapping("sell/{rewardId}/to/{clientId}")
    public CompletableFuture<ResponseEntity<HttpStatus>> assign(@PathVariable Long rewardId, @PathVariable Long clientId) {
        return CompletableFuture.completedFuture(service.assign(rewardId, clientId));
    }

    @GetMapping("{clientId}")
    public CompletableFuture<List<RewardItem>> parseItemsByClient(@PathVariable Long clientId) {
        return CompletableFuture.completedFuture(service.parseClientItems(clientId));
    }

    @PostMapping("rollback/{clientId}/item/{rewardId}")
    public CompletableFuture<ResponseEntity<HttpStatus>> rollbackReward(@PathVariable Long clientId, @PathVariable Long rewardId) {
        return CompletableFuture.completedFuture(service.rollback(clientId, rewardId));
    }
}
