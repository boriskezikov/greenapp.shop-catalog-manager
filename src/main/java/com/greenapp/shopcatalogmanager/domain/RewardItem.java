package com.greenapp.shopcatalogmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "RewardItem")
public class RewardItem implements Serializable {
    @Id
    private Long id;
    @Column
    private String title;
    @Column
    private String createdBy;
    @Column
    private Timestamp createdWhen;
    @Column
    private Timestamp lastUpdated;
    @Column
    private String description;
    @Column
    private Long price;
}
