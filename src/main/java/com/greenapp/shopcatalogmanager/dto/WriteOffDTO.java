package com.greenapp.shopcatalogmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WriteOffDTO {

    public Long clientId;
    public Long amount;
    public String initiator;
}
