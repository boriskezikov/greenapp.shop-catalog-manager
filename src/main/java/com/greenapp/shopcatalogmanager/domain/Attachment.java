package com.greenapp.shopcatalogmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Attachment")
public class Attachment {
    @Id
    private Long id;
    @Column
    private Long rewardId;
    @Column
    private Long contentLength;
    @Column
    private String contentType;
}