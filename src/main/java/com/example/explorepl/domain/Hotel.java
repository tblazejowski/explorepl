package com.example.explorepl.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Hotel implements Serializable {

    @Id
    @GeneratedValue
    private Integer Id;

    @Column
    private String name;

    @Column (length = 2000)
    private String description;

    @Column
    private String keywords;

    @Column
    private Integer price;

    @ManyToOne
    private Province province;

    @Column
    private StarsRating starsRating;

    public Hotel(String name, String description, String keywords, Integer price, Province province, StarsRating starsRating) {
        this.name = name;
        this.description = description;
        this.keywords = keywords;
        this.price = price;
        this.province = province;
        this.starsRating = starsRating;
    }

    protected Hotel(){
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public StarsRating getStarsRating() {
        return starsRating;
    }

    public void setStarsRating(StarsRating starsRating) {
        this.starsRating = starsRating;
    }
}
