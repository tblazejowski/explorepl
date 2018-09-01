package com.example.explorepl.repository;

import com.example.explorepl.domain.Province;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

public interface ProvinceRepository extends CrudRepository<Province, String> {
    Optional<Province> findByName(@Param("name") String name);

    @Override
    @RestResource (exported = false)
    <S extends Province> S save(S s);

    @Override
    @RestResource (exported = false)
    <S extends Province> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource (exported = false)
    void deleteById(String s);

    @Override
    @RestResource (exported = false)
    void delete(Province province);

    @Override
    @RestResource (exported = false)
    void deleteAll(Iterable<? extends Province> iterable);

    @Override
    @RestResource (exported = false)
    void deleteAll();
}
