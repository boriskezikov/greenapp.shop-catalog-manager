package com.greenapp.shopcatalogmanager.repository;

import com.greenapp.shopcatalogmanager.domain.RewardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<RewardItem, Long> {

    Optional<RewardItem> findById(Long id);
    Optional<List<RewardItem>> findAllByCreatedWhen(Timestamp createdWhen);
}
