package com.service.translator.service;

import com.service.translator.exception.OptimisticLockingFailureException;
import com.service.translator.exception.TranslationException;
import com.service.translator.model.TranslationRequest;
import com.service.translator.model.TranslationResponse;
import com.service.translator.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
/**
 * @author user
 * @year 2024
 */
@Service
public class TranslationService {

    private final RestTemplate restTemplate;
    private final TranslationRepository translationRepository;
    private final ExecutorService executorService;

    @Value("${yandex.api.key}")
    private String apiKey;

    @Value("${yandex.folder.id}")
    private String folderId;

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
        // Remove multithreading for testing
//        return translateWord(text, sourceLang, targetLang);
    }

    private String translateWord(String word, String sourceLang, String targetLang) throws TranslationException {
        String url = String.format("https://translate.api.cloud.yandex.net/translate/v2/translate?folderId=%s&targetLanguageCode=%s",
                folderId,
                targetLang);
        System.out.println("Constructed URL: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Api-Key " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("Request Headers: " + headers);

        String body = String.format("{\"folderId\": \"%s\", \"texts\": [\"%s\"], \"targetLanguageCode\": \"%s\"}",
                folderId, word, targetLang);
        System.out.println("Request Body: " + body);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<TranslationResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, TranslationResponse.class);
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<TranslationResponse.Translation> translations = response.getBody().getTranslations();
                if (translations != null && !translations.isEmpty()) {
                    return translations.get(0).getText();
                } else {
                    throw new TranslationException("Пустой ответ от API перевода");
                }
            } else {
                throw new TranslationException("Yandex API вернул ошибку: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            e.printStackTrace(); // Печатаем стек-трейс ошибки
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
