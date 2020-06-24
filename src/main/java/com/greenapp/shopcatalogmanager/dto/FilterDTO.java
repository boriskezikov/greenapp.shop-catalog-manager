package com.greenapp.shopcatalogmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterDTO {
    private Long price;
    private Timestamp createdWhen;
    private Long companyId;
}
