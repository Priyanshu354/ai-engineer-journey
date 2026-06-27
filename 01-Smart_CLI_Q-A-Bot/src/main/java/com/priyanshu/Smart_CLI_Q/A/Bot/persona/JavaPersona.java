package com.priyanshu.Smart_CLI_Q.A.Bot.persona;


import org.springframework.stereotype.Component;

@Component("java")
public class JavaPersona implements PersonaStrategy {
    @Override
    public String getSystemPrompt() {
        return "You are a JAVA instructor with 15+ year exp, " +
                "You will guide students in a very structure manner & Teach them JAVA";
    }
}
