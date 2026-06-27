package com.priyanshu.Smart_CLI_Q.A.Bot;

import com.priyanshu.Smart_CLI_Q.A.Bot.persona.PersonaFactory;
import com.priyanshu.Smart_CLI_Q.A.Bot.persona.PersonaStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Value("${OPEN_ROUTER_API_KEY:NOT_FOUND}")
    private String apiKey;

    @PostConstruct
    public void test() {
        System.out.println("API Key: " + apiKey);
    }

    private final ChatClient chatClient;
    private final ConversationHistory conversationHistory;
    private final PersonaFactory personaFactory;

    public ChatService(ChatModel chatModel, ConversationHistory conversationHistory, PersonaFactory personaFactory) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.conversationHistory = conversationHistory;
        this.personaFactory = personaFactory;
    }

    public String chat(String userPrompt, String persona) {

        if(userPrompt == null) return "null can't be there";

        String context = conversationHistory.previousContext();

//        return chatClient.prompt()
//                .user(" previous context : " + context + ",  latest prompt : " + userPrompt)
//                .call()
//                .content();

        StringBuilder response = new StringBuilder();

        PersonaStrategy strategy = personaFactory.getPersona(persona);
        String systemPrompt = strategy.getSystemPrompt();

        System.out.println("===== SYSTEM PROMPT =====");
        System.out.println(systemPrompt);
        System.out.println("=========================");

        chatClient.prompt()
                .system(systemPrompt)
                .user("previous context: " + context +
                        ", latest prompt: " + userPrompt)
                .stream()
                .content()
                .doOnNext(token -> {
                    System.out.print(token);
                    response.append(token);
                })
                .blockLast();

        System.out.println();

        return response.toString();
    }
}
