package com.priyanshu.PDF_Document_Chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PdfIngestionService {

    private final VectorStore vectorStore;

    public Map<String, Object> ingestPdf(MultipartFile file)
            throws Exception {

        long start = System.currentTimeMillis();

        Path tempFile = Files.createTempFile(
                "upload-",
                ".pdf"
        );

        try {

            // Save uploaded PDF
            file.transferTo(tempFile.toFile());


            // Read PDF
            PagePdfDocumentReader pdfReader =
                    new PagePdfDocumentReader(
                            new FileSystemResource(tempFile),
                            PdfDocumentReaderConfig.builder()
                                    .withPageExtractedTextFormatter(
                                            ExtractedTextFormatter.defaults()
                                    )
                                    .withPagesPerDocument(1)
                                    .build()
                    );

            List<Document> pages = pdfReader.get();


            // Split into 500-token chunks
            // No overlap
            TokenTextSplitter splitter =
                    TokenTextSplitter.builder()
                            .withChunkSize(500)
                            .withMinChunkSizeChars(350)
                            .withMinChunkLengthToEmbed(5)
                            .withMaxNumChunks(10000)
                            .build();

            List<Document> chunks =
                    splitter.apply(pages);


            // Add metadata
            String fileName =
                    file.getOriginalFilename();

            chunks.forEach(chunk ->
                    chunk.getMetadata()
                            .put("fileName", fileName)
            );


            // Generate embeddings + store in PGVector
            vectorStore.add(chunks);


            return Map.of(
                    "fileName", fileName,
                    "totalPages", pages.size(),
                    "totalChunks", chunks.size(),
                    "timeTakenMs",
                    System.currentTimeMillis() - start
            );

        } finally {

            // Always delete temporary PDF
            Files.deleteIfExists(tempFile);
        }
    }
}