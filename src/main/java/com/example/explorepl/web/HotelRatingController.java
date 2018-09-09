package com.example.explorepl.web;


import com.example.explorepl.domain.Hotel;
import com.example.explorepl.domain.HotelRating;
import com.example.explorepl.domain.HotelRatingPk;
import com.example.explorepl.repository.HotelRatingRepository;
import com.example.explorepl.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/hotels/{hotelId}/ratings")
public class HotelRatingController {

    HotelRatingRepository hotelRatingRepository;
    HotelRepository hotelRepository;

    @Autowired
    public HotelRatingController(HotelRatingRepository hotelRatingRepository, HotelRepository hotelRepository) {
        this.hotelRatingRepository = hotelRatingRepository;
        this.hotelRepository = hotelRepository;
    }

    protected HotelRatingController() {
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createHotelRating(@PathVariable(value = "hotelId") int hotelId, @RequestBody @Validated RatingDto ratingDto) {
        Hotel hotel = verifyTHotel(hotelId);
        hotelRatingRepository.save(new HotelRating(new HotelRatingPk(hotel, ratingDto.getCustomerId()), ratingDto.getScore(), ratingDto.getComment()));

    }

    @RequestMapping(method = RequestMethod.GET)
    public List<RatingDto> getAllRatingsForHotel(@PathVariable(name = "hotelId") int hotelId) {
        verifyTHotel(hotelId);
        return hotelRatingRepository.findByPkHotelId(hotelId).stream().map(hotelRating -> toDto(hotelRating))
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/average")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(name = "hotelId") int hotelId) {
        verifyTHotel(hotelId);
        List<HotelRating> hotelRatings = hotelRatingRepository.findByPkHotelId(hotelId);
        OptionalDouble average = hotelRatings.stream().mapToInt(HotelRating::getScore).average();
        return new AbstractMap.SimpleEntry<String, Double>("average", average.isPresent() ? average.getAsDouble() : null);
    }

    /**
     * Convert the HotelRating entity to a RatingDto
     *
     * @param hotelRating
     * @return RatingDto
     */
    private RatingDto toDto(HotelRating hotelRating) {
        return new RatingDto(hotelRating.getScore(), hotelRating.getComment(), hotelRating.getPk().getCustomerId());
    }

    /**
     * Verify and return the HotelRating for a particular hotelId and Customer
     *
     * @param hotelId
     * @param customerId
     * @return the found HotelRating
     * @throws NoSuchElementException if no HotelRating found
     */
    private HotelRating verifyHotelRating(int hotelId, int customerId) throws NoSuchElementException {
        HotelRating rating = hotelRatingRepository.findByPkHotelIdAndPkCustomerId(hotelId, customerId);
        if (rating == null) {
            throw new NoSuchElementException("Hotel-Rating pair for request("
                    + hotelId + " for customer" + customerId);
        }
        return rating;
    }

    /**
     * Verify and return the Hotel given a hotelId.
     *
     * @param hotelId
     * @return the found Hotel
     * @throws NoSuchElementException if no Hotel found.
     */
    private Hotel verifyTHotel(int hotelId) throws NoSuchElementException {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (!hotel.isPresent()) {
            throw new NoSuchElementException("Tour with id " + hotelId + " does not exist!");
        }
        return hotel.get();
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }
}
