package com.service.translator.controller;

import com.service.translator.exception.TranslationException;
import com.service.translator.model.TranslationRequest;
import com.service.translator.dto.TranslationRequestDTO; // Add DTO for request body
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
            @RequestBody TranslationRequestDTO requestDTO,
            HttpServletRequest request) {

        String clientIp = request.getRemoteAddr();

        try {
            String translatedText = translationService.translateText(
                    requestDTO.getText(),
                    requestDTO.getTargetLang(),
                    clientIp
            );
            return ResponseEntity.ok(translatedText);
        } catch (TranslationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
