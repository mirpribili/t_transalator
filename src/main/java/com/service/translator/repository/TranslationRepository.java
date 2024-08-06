package com.service.translator.repository;

import com.service.translator.exception.OptimisticLockingFailureException;
import com.service.translator.model.TranslationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author user
 * @year 2024
 */
@Repository
public class TranslationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TranslationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(TranslationRequest request) {
        String sql = "INSERT INTO translation_requests (client_ip, input_text, result_text, version) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, request.getClientIp(), request.getInputText(), request.getResultText(), request.getVersion());
    }

    public void update(TranslationRequest request) {
        String sql = "UPDATE translation_requests SET client_ip = ?, input_text = ?, result_text = ?, version = version + 1 WHERE id = ? AND version = ?";
        int rowsAffected = jdbcTemplate.update(sql, request.getClientIp(), request.getInputText(), request.getResultText(), request.getId(), request.getVersion());
        if (rowsAffected == 0) {
            throw new OptimisticLockingFailureException("Запись была изменена другим пользователем");
        }
    }

    public List<TranslationRequest> findAll() {
        String sql = "SELECT * FROM translation_requests";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new TranslationRequest(
                rs.getLong("id"),
                rs.getString("client_ip"),
                rs.getString("input_text"),
                rs.getString("result_text"),
                rs.getTimestamp("request_time"),
                rs.getInt("version")
        ));
    }
}
