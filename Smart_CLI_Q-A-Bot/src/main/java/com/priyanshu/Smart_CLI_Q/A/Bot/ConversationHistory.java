package com.priyanshu.Smart_CLI_Q.A.Bot;

import org.springframework.stereotype.Component;

class Node {
    String userPrompt;
    String llmResponse;
    Node next;

    Node(String userPrompt, String llmResponse) {
        this.userPrompt = userPrompt;
        this.llmResponse = llmResponse;
        this.next = null;
    }
}

@Component
public class ConversationHistory {
    Node head = null;
    Node tail = null;
    int len = 0;

    public void insert(Node node) {
        if(head == null){
            head = node;
            tail = node;
            len = 1;
            return ;
        }

        if(len == 10) {
            head = head.next;
            len--;
        }

        tail.next = node;
        tail = node;
        len++;
    }

    public String previousContext() {
        Node curr = head;

        StringBuilder context = new StringBuilder();

        while(curr != null) {
            context.append(curr.userPrompt + "->" + curr.llmResponse + ", ");
            curr = curr.next;
        }

        return context.toString();
    }

}

