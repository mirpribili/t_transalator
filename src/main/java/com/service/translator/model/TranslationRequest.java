package com.service.translator.model;

import java.sql.Timestamp;
/**
 * @author user
 * @year 2024
 */
public class TranslationRequest {
    private Long id;
    private String clientIp;
    private String inputText;
    private String resultText;
    private Timestamp requestTime;
    private int version;

    public TranslationRequest() {
    }

    public TranslationRequest(Long id, String clientIp, String inputText, String resultText, Timestamp requestTime, int version) {
        this.id = id;
        this.clientIp = clientIp;
        this.inputText = inputText;
        this.resultText = resultText;
        this.requestTime = requestTime;

        this.version = version;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
