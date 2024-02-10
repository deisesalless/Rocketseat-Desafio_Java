package com.deisesales.certification_nlw;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraController")
public class PrimeiraController {

    @GetMapping("/retornarPrimeiraController")
    public Usuario retornoPrimeiraController() {
        Usuario usuario = new Usuario("Deise", 28);
        return usuario;
    }

    @PostMapping("/meuPrimeiroPost")
    public String primeiroPost() {
        return "meu primeiro Post";
    }

    record Usuario(String nome, int idade) {}
}
