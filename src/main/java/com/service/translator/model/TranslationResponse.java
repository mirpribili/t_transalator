package com.service.translator.model;

import java.util.List;

/**
 * @author user
 * @year 2024
 */
public class TranslationResponse {
    private List<Translation> translations;

    public TranslationResponse() {}

    public TranslationResponse(List<Translation> translations) {
        this.translations = translations;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public static class Translation {
        private String text;
        private String detectedLanguageCode;

        public Translation() {}

        public Translation(String text, String detectedLanguageCode) {
            this.text = text;
            this.detectedLanguageCode = detectedLanguageCode;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getDetectedLanguageCode() {
            return detectedLanguageCode;
        }

        public void setDetectedLanguageCode(String detectedLanguageCode) {
            this.detectedLanguageCode = detectedLanguageCode;
        }
    }
}
