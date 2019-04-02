package org.prime.internship.service;

import org.prime.internship.entity.City;
import org.prime.internship.repository.CityRepository;

class CityService {
    private final CityRepository cityRepository;

    CityService() {
        this.cityRepository = new CityRepository();
    }

    City processCityToDB(String name) {
        City city;
        //Check if city exists in DB, if not, use INSERT repository method
        if (cityRepository.getOneByName(name) != null) {
            city = cityRepository.getOneByName(name);
            cityRepository.update(city);
        } else {
            // If city already exists in DB, use UPDATE repository method
            city = new City();
            city.setName(name);
            city.setCityId(cityRepository.insert(city).getCityId());
        }
        return city;
    }

}