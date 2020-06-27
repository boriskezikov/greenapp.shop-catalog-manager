package com.greenapp.shopcatalogmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "RewardItems")
public class RewardItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private Long createdBy;
    @Column
    private Timestamp createdWhen;
    @Column
    private Timestamp lastUpdated;
    @Column
    private String description;
    @Column
    private Long price;
    @Column
    private byte[] headerPhoto;
    @Column
    private SoldStatus status;
    @Column
    private Integer amount;


    public void decrementAmount() {
        this.amount -= 1;
    }

    public void incrementAmount() {
        this.amount += 1;
    }
}
