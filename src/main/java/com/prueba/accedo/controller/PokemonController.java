package com.prueba.accedo.controller;

import com.prueba.accedo.entity.Pokemon;
import com.prueba.accedo.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/pokemons")
    public String getAllPokemons(Model model) {
        List<Pokemon> pokemons = pokemonService.getAllPokemons();
        model.addAttribute("pokemons", pokemons);
        return "pokemons";
    }
}
