package com.service.translator.controller;

import com.service.translator.model.TranslationRequest;
import com.service.translator.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * @author user
 * @year 2024
 */
@RestController
@RequestMapping("/api/translation-requests")
public class TranslationRequestController {

    private final TranslationRepository translationRepository;

    @Autowired
    public TranslationRequestController(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @GetMapping
    public List<TranslationRequest> getAllTranslationRequests() {
        return translationRepository.findAll();
    }
}

