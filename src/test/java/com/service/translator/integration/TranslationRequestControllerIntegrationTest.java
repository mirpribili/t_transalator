package com.service.translator.integration;

import com.service.translator.model.TranslationRequest;
import com.service.translator.repository.TranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author user
 * @year 2024
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional // Откат транзакций после выполнения теста
public class TranslationRequestControllerIntegrationTest {

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        // Очистка таблицы перед вставкой тестовых данных
        jdbcTemplate.update("DELETE FROM translation_requests");

        // Добавляем тестовые данные в базу данных
        TranslationRequest request1 = new TranslationRequest();
        request1.setClientIp("192.168.1.1");
        request1.setInputText("Hello world");
        request1.setResultText("Привет мир");
        request1.setRequestTime(new Timestamp(System.currentTimeMillis()));
        request1.setVersion(0);
        translationRepository.save(request1);

        TranslationRequest request2 = new TranslationRequest();
        request2.setClientIp("192.168.1.2");
        request2.setInputText("Good morning");
        request2.setResultText("Доброе утро");
        request2.setRequestTime(new Timestamp(System.currentTimeMillis()));
        request2.setVersion(0);
        translationRepository.save(request2);

        TranslationRequest request3 = new TranslationRequest();
        request3.setClientIp("192.168.1.3");
        request3.setInputText("How are you");
        request3.setResultText("Как дела");
        request3.setRequestTime(new Timestamp(System.currentTimeMillis()));
        request3.setVersion(0);
        translationRepository.save(request3);
    }

    @Test
    public void testGetAllTranslationRequests() {
        List<TranslationRequest> requests = translationRepository.findAll();

        // Проверяем, что список не пустой
        assertThat(requests).isNotEmpty();

        // Проверяем, что количество элементов равно 3
        assertThat(requests.size()).isEqualTo(3);

        // Проверяем наличие конкретных значений в списке, игнорируя порядок и возможные дубликаты
        List<String> clientIps = requests.stream()
                .map(TranslationRequest::getClientIp)
                .toList();

        assertThat(clientIps).containsExactlyInAnyOrder("192.168.1.1", "192.168.1.2", "192.168.1.3");

        // Проверяем конкретные значения для каждого запроса
        TranslationRequest request1 = requests.stream()
                .filter(r -> "192.168.1.1".equals(r.getClientIp()))
                .findFirst()
                .orElse(null);
        assertThat(request1).isNotNull();
        assertThat(request1.getInputText()).isEqualTo("Hello world");
        assertThat(request1.getResultText()).isEqualTo("Привет мир");

        TranslationRequest request2 = requests.stream()
                .filter(r -> "192.168.1.2".equals(r.getClientIp()))
                .findFirst()
                .orElse(null);
        assertThat(request2).isNotNull();
        assertThat(request2.getInputText()).isEqualTo("Good morning");
        assertThat(request2.getResultText()).isEqualTo("Доброе утро");

        TranslationRequest request3 = requests.stream()
                .filter(r -> "192.168.1.3".equals(r.getClientIp()))
                .findFirst()
                .orElse(null);
        assertThat(request3).isNotNull();
        assertThat(request3.getInputText()).isEqualTo("How are you");
        assertThat(request3.getResultText()).isEqualTo("Как дела");
    }
}
