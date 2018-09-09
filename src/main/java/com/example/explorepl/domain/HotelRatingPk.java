package com.example.explorepl.domain;


/**
* Hotel Rating Primary Key containing a Hotel and a Customer Identifier
*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HotelRatingPk implements Serializable {

    @ManyToOne
    private Hotel hotel;

    @Column(insertable = false, updatable = false, nullable = false)
    private Integer customerId;

    /**
    *
    * Fully initialize a Hotel Rating Pk
    *
    * @param hotel          the hotel
    * @param customerId     the customer identifier
    */

    public HotelRatingPk(Hotel hotel, Integer customerId) {
        this.hotel = hotel;
        this.customerId = customerId;
    }

    public HotelRatingPk() {
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelRatingPk that = (HotelRatingPk) o;
        return Objects.equals(hotel, that.hotel) &&
                Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotel, customerId);
    }

    @Override
    public String toString() {
        return "HotelRatingPk{" +
                "hotel=" + hotel +
                ", customerId=" + customerId +
                '}';
    }
}
