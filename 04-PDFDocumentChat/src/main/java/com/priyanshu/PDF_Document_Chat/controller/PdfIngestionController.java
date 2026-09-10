package com.priyanshu.PDF_Document_Chat.controller;

import com.priyanshu.PDF_Document_Chat.service.PdfIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PdfIngestionController {

    private final PdfIngestionService pdfIngestionService;

    @PostMapping("/pdf/upload")
    public Map<String, Object> pdfUpload(@RequestParam("file") MultipartFile pdf) throws Exception {
        return pdfIngestionService.ingestPdf(pdf);
    }

}
