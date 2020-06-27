package com.greenapp.shopcatalogmanager.service;

import com.greenapp.shopcatalogmanager.domain.RewardItem;
import com.greenapp.shopcatalogmanager.dto.ClientMailDTO;
import com.greenapp.shopcatalogmanager.dto.RewardMailDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.greenapp.shopcatalogmanager.configuration.MailTopics.REWARD_CONFIRMATION;

@Service
@RequiredArgsConstructor
public class MailService {
    private final AuthClient authClient;
//    private final KafkaTemplate<String, RewardMailDTO> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(MailService.class.getName());

    public void sendConfirmation(RewardItem rewardItem, Long clientId) {

        var clientDto = authClient.findAccountByClientId(clientId);
        if (Objects.nonNull(clientDto)) {
            var rewardMail = RewardMailDTO.builder()
                    .mailAddress(clientDto.getMailAddress())
                    .rewardDescription(rewardItem.getDescription())
                    .rewardTitle(rewardItem.getTitle())
                    .rewardPrice(rewardItem.getPrice())
                    .name(clientDto.getName())
                    .surname(clientDto.getSurname())
                    .build();

//            kafkaTemplate.send(REWARD_CONFIRMATION, rewardMail);
            log.info("Confirmation sent to {}", rewardMail.getMailAddress());
        }
    }
}

