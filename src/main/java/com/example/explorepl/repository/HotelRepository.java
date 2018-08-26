package com.example.explorepl.repository;

import com.example.explorepl.domain.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository <Hotel, Integer> {
}
