package com.example.explorepl.services;

import com.example.explorepl.domain.Hotel;
import com.example.explorepl.domain.Province;
import com.example.explorepl.domain.StarsRating;
import com.example.explorepl.repository.HotelRepository;
import com.example.explorepl.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelService {

    private ProvinceRepository provinceRepository;
    private HotelRepository hotelRepository;

    @Autowired
    public HotelService(ProvinceRepository provinceRepository, HotelRepository hotelRepository) {
        this.provinceRepository = provinceRepository;
        this.hotelRepository = hotelRepository;
    }

    public Hotel createHotel(String name, String description, String keywords, Integer price,
                             String provinceCode, StarsRating starsRating) {

        Optional<Province> province = provinceRepository.findById(provinceCode);
        if (province.isPresent()) {
            return hotelRepository.save(new Hotel(name, description, keywords, price,
                    province.get(), starsRating));
        }
        throw new RuntimeException("Province with code: " + provinceCode + " does not exists.");
    }
    public Iterable<Hotel> lookup (){
        return hotelRepository.findAll();
    }
    public long total(){
        return hotelRepository.count();
    }
}
