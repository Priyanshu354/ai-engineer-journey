package com.priyanshu.Smart_CLI_Q.A.Bot.persona;


import org.springframework.stereotype.Component;

@Component("interview")
public class InterviewPersona implements PersonaStrategy {
    @Override
    public String getSystemPrompt() {
        return "You are a Interviewer with 15+ year exp, " +
                "You will guide students & Take a extensive interveiw";
    }
}

