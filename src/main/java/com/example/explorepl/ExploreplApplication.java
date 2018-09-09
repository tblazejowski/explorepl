package com.example.explorepl;

import com.example.explorepl.domain.Hotel;
import com.example.explorepl.domain.Province;
import com.example.explorepl.domain.StarsRating;
import com.example.explorepl.repository.HotelRepository;
import com.example.explorepl.repository.ProvinceRepository;
import com.example.explorepl.services.HotelService;
import com.example.explorepl.services.ProvinceService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ExploreplApplication implements CommandLineRunner {

    @Autowired
    ProvinceService provinceService;
    @Autowired
    HotelService hotelService;

    public static void main(String[] args) {
        SpringApplication.run(ExploreplApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Create the default provinces in Poland
        provinceService.createProvince("BI", "Podlasie");
        provinceService.createProvince("CT", "Kujawy");
        provinceService.createProvince("DW", "Dolny Śląsk");
        provinceService.createProvince("EL", "Centrum");
        provinceService.createProvince("FZ", "Lubuskie");
        provinceService.createProvince("GA", "Pomorze");
        provinceService.createProvince("KR", "Małopolska");
        provinceService.createProvince("LU", "Lubelszczyzna");
        provinceService.createProvince("NO", "Warmia-Mazury");
        provinceService.createProvince("OP", "Opolszczyzna");
        provinceService.createProvince("PO", "Wielkopolska");
        provinceService.createProvince("RZ", "Podkarpacie");
        provinceService.createProvince("SK", "Śląsk");
        provinceService.createProvince("TK", "Kielecczyzna");
        provinceService.createProvince("WA", "Mazowsze");
        provinceService.createProvince("ZS", "Pomorze Zachodnie");
        System.out.println("Number of provinces: " + provinceService.total());
        HotelFromFile.importHotels().forEach(hotel -> hotelService.createHotel(
                hotel.name,
                hotel.description,
                hotel.keywords,
                Integer.parseInt(hotel.price),
                hotel.province,
                StarsRating.valueOf(hotel.starsRating)));
        System.out.println("Number of hotels: " + hotelService.total());
    }

    /**
     * Helper class to import the records in ExplorePoland.json file
     *
     */
    static class HotelFromFile {
        //attributes as listed in JSON file
        private String name, description, keywords, price, province, starsRating;
        /**
         * Open the ExplorePoland.json, decode every entry into a Hotel Object
         *
         * @return a List of HotelFromFile Objects
         * @throws java.io.IOException if ObjectMapper unable to open file
         * */
        static List<HotelFromFile> importHotels() throws IOException {
            return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).
                    readValue(HotelFromFile.class.getResourceAsStream("/ExplorePoland.json"), new TypeReference<List<HotelFromFile>>(){});
        }
    }
}
