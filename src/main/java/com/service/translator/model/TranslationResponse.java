package com.service.translator.model;

import java.util.List;
/**
 * @author user
 * @year 2024
 */
public class TranslationResponse {
    private List<String> translations;

    public TranslationResponse() {}

    public List<String> getText() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }
}
