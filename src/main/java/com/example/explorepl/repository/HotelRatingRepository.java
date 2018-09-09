package com.example.explorepl.repository;

import com.example.explorepl.domain.HotelRating;
import com.example.explorepl.domain.HotelRatingPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface HotelRatingRepository extends CrudRepository<HotelRating, HotelRatingPk> {

    /**
     *
     * Lookup all the HotelRatings for a hotel
     *
     * @param hotelId is the hotel Identifier
     * @return a List of any found HotelRatings;
     */
    List<HotelRating> findByPkHotelId (Integer hotelId);

    /**
     *
     * Lookup a HotelRating by the HotelId and Customer Id
     *
     * @param hotelId is the hotel Identifier
     * @param customerId is the customer Identifier
     * @return HotelRating if found, null otherwise
     */
    HotelRating findByPkHotelIdAndPkCustomerId (Integer hotelId, Integer customerId);
}
