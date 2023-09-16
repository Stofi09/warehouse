package com.warehouse.warehouse.model;

import jakarta.persistence.*;

@Entity
@Table(name = "market_user")
public class MarketUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}