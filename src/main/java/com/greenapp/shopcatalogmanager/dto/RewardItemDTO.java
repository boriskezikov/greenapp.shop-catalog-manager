package com.greenapp.shopcatalogmanager.dto;

import lombok.Data;

@Data
public class RewardItemDTO {

    private String title;
    private String createdBy;
    private String description;
    private Long price;
    private byte[] content;

}
