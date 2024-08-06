package com.service.translator.service;

import com.service.translator.exception.OptimisticLockingFailureException;
import com.service.translator.exception.TranslationException;
import com.service.translator.model.TranslationRequest;
import com.service.translator.model.TranslationResponse;
import com.service.translator.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
/**
 * @author user
 * @year 2024
 */
@Service
public class TranslationService {

    private final RestTemplate restTemplate;
    private final TranslationRepository translationRepository;
    private final ExecutorService executorService;

    @Autowired
    public TranslationService(RestTemplateBuilder restTemplateBuilder, TranslationRepository translationRepository, ExecutorService executorService) {
        this.restTemplate = restTemplateBuilder.build();
        this.translationRepository = translationRepository;
        this.executorService = executorService;
    }

    public String translateText(String text, String sourceLang, String targetLang, String clientIp) throws TranslationException {
        String[] words = text.split("\\s+");
        List<Future<String>> futures = new ArrayList<>();

        for (String word : words) {
            futures.add(executorService.submit(() -> translateWord(word, sourceLang, targetLang)));
        }

        StringBuilder translatedText = new StringBuilder();

        try {
            for (Future<String> future : futures) {
                translatedText.append(future.get()).append(" ");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new TranslationException("Ошибка при переводе", e);
        }

        String result = translatedText.toString().trim();
        saveTranslationRequest(clientIp, text, result);

        return result;
    }

    private String translateWord(String word, String sourceLang, String targetLang) throws TranslationException {
        // Реализация вызова внешнего сервиса перевода
        String url = String.format("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20190115T093726Z.65e1460d8d95bd06.р45ор345о3р4о53р45о345р3о&text=%s&lang=%s-%s", word, sourceLang, targetLang);

        try {
            ResponseEntity<TranslationResponse> response = restTemplate.getForEntity(url, TranslationResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getText().get(0);
            } else {
                throw new TranslationException("Ошибка доступа к ресурсу перевода");
            }
        } catch (RestClientException e) {
            throw new TranslationException("Ошибка доступа к ресурсу перевода", e);
        }
    }

    private void saveTranslationRequest(String clientIp, String inputText, String resultText) {
        TranslationRequest translationRequest = new TranslationRequest();
        translationRequest.setClientIp(clientIp);
        translationRequest.setInputText(inputText);
        translationRequest.setResultText(resultText);
        translationRequest.setVersion(0); // Устанавливаем начальное значение версии

        try {
            translationRepository.save(translationRequest);
        } catch (OptimisticLockingFailureException e) {
            throw new TranslationException("Конфликт обновления записи", e);
        }
    }
}
