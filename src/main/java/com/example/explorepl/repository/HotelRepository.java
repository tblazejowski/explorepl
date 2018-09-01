package com.example.explorepl.repository;

import com.example.explorepl.domain.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Integer> {
    Page<Hotel> findByProvinceCode (@Param("code") String code, Pageable pageable);
}
