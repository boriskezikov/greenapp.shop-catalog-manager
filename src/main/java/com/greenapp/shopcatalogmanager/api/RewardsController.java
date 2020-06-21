package com.greenapp.shopcatalogmanager.api;

import com.greenapp.shopcatalogmanager.domain.RewardItem;
import com.greenapp.shopcatalogmanager.dto.RewardItemDTO;
import com.greenapp.shopcatalogmanager.service.RewardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("rewards/")
public class RewardsController {

    private final RewardsService service;

    @GetMapping("load/all")
    public CompletableFuture<List<RewardItem>> loadShop(){
        return service.loadAllItems();
    }

    @PostMapping("create")
    public ResponseEntity<RewardItem> createNewRewardItem(@Valid @RequestBody RewardItemDTO dto){
        return service.createRewardItem(dto);
    }
}
