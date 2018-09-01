package com.example.explorepl.repository;

import com.example.explorepl.domain.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Integer> {
    Page<Hotel> findByProvinceCode (@Param("code") String code, Pageable pageable);

    @Override
    @RestResource(exported = false)
    <S extends Hotel> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends Hotel> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer);

    @Override
    @RestResource(exported = false)
    void delete(Hotel hotel);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Hotel> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
