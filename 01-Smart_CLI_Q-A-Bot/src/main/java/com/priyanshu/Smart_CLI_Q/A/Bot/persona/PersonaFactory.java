package com.priyanshu.Smart_CLI_Q.A.Bot.persona;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PersonaFactory {

    private final Map<String, PersonaStrategy> personas;

    public PersonaFactory(Map<String, PersonaStrategy> personas) {
        this.personas = personas;
    }

    public PersonaStrategy getPersona(String name) {
        return personas.get(name.toLowerCase());
    }
}
