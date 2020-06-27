package com.greenapp.shopcatalogmanager.configuration;


import static com.greenapp.shopcatalogmanager.kafka.KafkaConfigConstants.TOPIC_PREFIX;

public interface MailTopics {
    String REWARD_CONFIRMATION = TOPIC_PREFIX + "sold-reward";
}
