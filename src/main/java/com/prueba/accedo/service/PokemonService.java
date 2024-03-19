package com.prueba.accedo.service;

import com.prueba.accedo.entity.Pokemon;
import com.prueba.accedo.entity.PokemonListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class PokemonService {

    private static final String API_BASE_URL = "https://pokeapi.co/api/v2/";

    public List<Pokemon> getAllPokemons() {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = API_BASE_URL + "pokemon/";
        ResponseEntity<PokemonListResponse> response = restTemplate.getForEntity(apiUrl, PokemonListResponse.class);

        System.out.println("Status Code: " + response.getStatusCode());

        if (response.getStatusCode() == HttpStatus.OK) {
            PokemonListResponse responseBody = response.getBody();
            System.out.println("Response Body: " + responseBody);

            if (responseBody != null && responseBody.getResults() != null && !responseBody.getResults().isEmpty()) {
                return responseBody.getResults();
            }
        } else {
            System.out.println("Error occurred: " + response.getStatusCode());
        }

        return Collections.emptyList();
    }
}
