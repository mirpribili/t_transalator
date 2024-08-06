package com.service.translator.dto;

/**
 * @author user
 * @year 2024
 */
public class TranslationRequestDTO {

    private String sourceLang;
    private String targetLang;
    private String text;

    // Getters and setters
    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
