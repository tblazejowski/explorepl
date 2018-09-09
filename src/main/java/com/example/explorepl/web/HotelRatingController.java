package com.example.explorepl.web;


import com.example.explorepl.domain.Hotel;
import com.example.explorepl.domain.HotelRating;
import com.example.explorepl.domain.HotelRatingPk;
import com.example.explorepl.repository.HotelRatingRepository;
import com.example.explorepl.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<RatingDto> getAllRatingsForHotel(@PathVariable(value = "hotelId") int hotelId, Pageable pageable) {
        verifyTHotel(hotelId);
        Page<HotelRating> hotelRatingPage = hotelRatingRepository.findByPkHotelId(hotelId, pageable);
        List<RatingDto> ratingDtoList = hotelRatingPage.getContent().stream().map(hotelRating -> toDto(hotelRating))
                .collect(Collectors.toList());
        return new PageImpl<RatingDto>(ratingDtoList, pageable, hotelRatingPage.getTotalPages());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/average")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable(value = "hotelId") int hotelId) {
        verifyTHotel(hotelId);
        List<HotelRating> hotelRatings = hotelRatingRepository.findByPkHotelId(hotelId);
        OptionalDouble average = hotelRatings.stream().mapToInt(HotelRating::getScore).average();
        return new AbstractMap.SimpleEntry<String, Double>("average", average.isPresent() ? average.getAsDouble() : null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RatingDto updateWithPut (@PathVariable(value = "hotelId") int hotelId, @RequestBody @Validated RatingDto ratingDto) {
        HotelRating hotelRating = verifyHotelRating(hotelId, ratingDto.getCustomerId());
        hotelRating.setScore(ratingDto.getScore());
        hotelRating.setComment(ratingDto.getComment());
        return toDto(hotelRatingRepository.save(hotelRating));
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public RatingDto updateWithPatch (@PathVariable(value = "hotelId") int hotelId, @RequestBody @Validated RatingDto ratingDto) {
        HotelRating hotelRating = verifyHotelRating(hotelId, ratingDto.getCustomerId());
        if (ratingDto.getScore() != null) {
            hotelRating.setScore(ratingDto.getScore());
        }
        if (ratingDto.getComment() != null) {
            hotelRating.setComment(ratingDto.getComment());
        }
        return toDto(hotelRatingRepository.save(hotelRating));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{customerId}")
    public void delete (@PathVariable(value = "hotelId") int hotelId, @PathVariable(value = "customerId") int customerId) {
        HotelRating hotelRating = verifyHotelRating(hotelId, customerId);
        hotelRatingRepository.delete(hotelRating);
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
            throw new NoSuchElementException("Hotel-Rating pair for requested hotel id: "
                    + hotelId + " and for customer with id: " + customerId);
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
