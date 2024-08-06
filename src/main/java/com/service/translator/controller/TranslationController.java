package com.service.translator.controller;

import com.service.translator.exception.TranslationException;
import com.service.translator.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
/**
 * @author user
 * @year 2024
 */
@RestController
@RequestMapping("/api/v1/translate")
public class TranslationController {

    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public ResponseEntity<String> translate(
            @RequestParam String sourceLang,
            @RequestParam String targetLang,
            @RequestBody String text,
            HttpServletRequest request) {

        String clientIp = request.getRemoteAddr();

        try {
            String translatedText = translationService.translateText(text, sourceLang, targetLang, clientIp);
            return ResponseEntity.ok(translatedText);
        } catch (TranslationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


