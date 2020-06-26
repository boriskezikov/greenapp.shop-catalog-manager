package com.greenapp.shopcatalogmanager.repository;

import com.greenapp.shopcatalogmanager.domain.ClientRewards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientsRepository extends JpaRepository<ClientRewards, Long> {

    List<ClientRewards> findAllByClientId(Long clientId);

    void deleteByClientIdAndRewardId(Long clientId, Long rewardId);

}
