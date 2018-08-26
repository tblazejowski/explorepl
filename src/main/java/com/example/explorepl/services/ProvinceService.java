package com.example.explorepl.services;

import com.example.explorepl.domain.Province;
import com.example.explorepl.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {

    private ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public Province createProvince(String code, String name) {
        if (!provinceRepository.existsById(code)) {
            provinceRepository.save(new Province(code, name));
        }
        return null;
    }
    public Iterable<Province> lookup (){
        return provinceRepository.findAll();
    }
    public long total(){
        return provinceRepository.count();
    }
}
