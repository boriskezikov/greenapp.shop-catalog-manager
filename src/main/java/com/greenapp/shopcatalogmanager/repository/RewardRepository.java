package com.greenapp.shopcatalogmanager.repository;

import com.greenapp.shopcatalogmanager.domain.RewardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<RewardItem, Long> {

    Optional<RewardItem> findById(Long id);
}
