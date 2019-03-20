package org.prime.internship.service;

import org.prime.internship.entity.City;
import org.prime.internship.repository.CityRepository;

public class CityService {
    private CityRepository cityRepository;

    CityService(){
        this.cityRepository = new CityRepository();
    }

    City processCityToDB (String name){
        City city;
        if (cityRepository.getOneByName(name) == null) {
            city = new City();
            city.setName(name);
            city.setCityId(cityRepository.insert(city).getCityId());
        }else {
            city = cityRepository.getOneByName(name);
            cityRepository.update(city);
        }
        return city;
    }

}
