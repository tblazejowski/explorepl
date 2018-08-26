package com.example.explorepl.repository;

import com.example.explorepl.domain.Province;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProvinceRepository extends CrudRepository <Province, String> {
    Optional<Province> findByName (String name);
}
