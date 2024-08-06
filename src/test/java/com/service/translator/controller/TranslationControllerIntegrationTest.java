package com.service.translator.controller;

import com.service.translator.dto.TranslationRequestDTO;
import com.service.translator.model.TranslationResponse;
import com.service.translator.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author user
 * @year 2024
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TranslationControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testTranslateEndpoint() {
        // Устанавливаем URL с портом
        String url = "http://localhost:" + port + "/api/v1/translate";

        // Создаем DTO для запроса
        TranslationRequestDTO requestDTO = new TranslationRequestDTO();
        requestDTO.setSourceLang("en");
        requestDTO.setTargetLang("ru");
        requestDTO.setText("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30");

        // Создаем заголовки и тело запроса
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TranslationRequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

        // Выполняем запрос
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Проверяем статус ответа
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Проверяем тело ответа
        assertThat(response.getBody()).isEqualTo("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30"); // Замените на ожидаемый результат перевода
    }
}
