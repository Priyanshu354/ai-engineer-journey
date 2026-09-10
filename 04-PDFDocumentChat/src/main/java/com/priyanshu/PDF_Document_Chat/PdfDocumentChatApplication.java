package com.priyanshu.PDF_Document_Chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdfDocumentChatApplication {

	public static void main(String[] args) {
        System.out.println(
                "API KEY EXISTS: " +
                        (System.getenv("OPENAI_API_KEY") != null)
        );
		SpringApplication.run(PdfDocumentChatApplication.class, args);
	}

}
