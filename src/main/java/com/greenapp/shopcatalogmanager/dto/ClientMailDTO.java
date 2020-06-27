package com.greenapp.shopcatalogmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientMailDTO {

    private String name;
    private String surname;
    private String mailAddress;
}
