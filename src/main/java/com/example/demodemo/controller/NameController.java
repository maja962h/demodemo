package com.example.demodemo.controller;

import com.example.demodemo.dto.Age;
import com.example.demodemo.dto.Gender;
import com.example.demodemo.dto.Nationality;
import com.example.demodemo.dto.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class NameController {

   @RequestMapping("name-info")
   public Response getDetails(@RequestParam String name){

       var resMono = Mono.zip(getAgeForName(name), getGenderForName(name), getNationalityForName(name)).map(t -> {
           Response r = new Response();
           r.setAge(t.getT1().getAge());
           r.setAgeCount(t.getT1().getCount());

           r.setGender(t.getT2().getGender());
           r.setGenderProbability(t.getT2().getProbability());

           r.setCountry(t.getT3().getCountry().get(0).getCountryId());
           r.setCountryProbability(t.getT3().getCountry().get(0).getProbability());
           return r;
       });

       Response res = resMono.block();
       res.setName(name);

       return res;
   }


    Mono<Age> getAgeForName(String name) {
        WebClient client = WebClient.create();
        Mono<Age> age = client.get()
                .uri("https://api.agify.io?name=" + name)
                .retrieve()
                .bodyToMono(Age.class);
        return age;
    }

    Mono<Gender> getGenderForName(String name) {
        WebClient client = WebClient.create();
        Mono<Gender> gender = client.get()
                .uri("https://api.genderize.io?name="+name)
                .retrieve()
                .bodyToMono(Gender.class);
        return gender;
    }

    Mono<Nationality> getNationalityForName(String name) {
        WebClient client = WebClient.create();
        Mono<Nationality> nationality = client.get()
                .uri("https://api.nationalize.io?name=" + name)
                .retrieve()
                .bodyToMono(Nationality.class);
        return nationality;
    }





}
