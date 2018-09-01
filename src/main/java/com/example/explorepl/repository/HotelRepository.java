package com.example.explorepl.repository;

import com.example.explorepl.domain.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends CrudRepository <Hotel, Integer> {
    List<Hotel> findByProvinceCode (@Param("code") String code);
}
