package com.service.translator.model;

import java.util.List;
/**
 * @author user
 * @year 2024
 */
public class TranslationResponse {
    private int code;
    private String lang;
    private List<String> text;

    public TranslationResponse() {}

    public TranslationResponse(int code, String lang, List<String> text) {
        this.code = code;
        this.lang = lang;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}

