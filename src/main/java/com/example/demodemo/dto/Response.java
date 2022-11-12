package com.example.demodemo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {

    String name;
    String gender;
    double genderProbability;
    int age;
    int ageCount;
    String country;
    double countryProbability;

    public void setGenderProbability(double p){
        genderProbability = p * 100;
    }

    public void setCountryProbability(double p){
        countryProbability = p * 100;
    }
}
