package com.example.explorepl.domain;

/**
 * Rating of a hotel by customer.
 */

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class HotelRating {

    @EmbeddedId
    private HotelRatingPk pk;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    /**
     * Create fully initialized HotelRating
     *
     * @param pk      primary key of a hotel and customer id
     * @param score   Integer score (1-5)
     * @param comment Optional comment from the customer
     */

    public HotelRating(HotelRatingPk pk, Integer score, String comment) {
        this.pk = pk;
        this.score = score;
        this.comment = comment;
    }

    protected HotelRating() {
    }

    @Override
    public String toString() {
        return "HotelRating{" +
                "pk=" + pk +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelRating that = (HotelRating) o;
        return Objects.equals(pk, that.pk) &&
                Objects.equals(score, that.score) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, score, comment);
    }

    public HotelRatingPk getPk() {
        return pk;
    }

    public void setPk(HotelRatingPk pk) {
        this.pk = pk;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
