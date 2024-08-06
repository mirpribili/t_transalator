CREATE TABLE IF NOT EXISTS translation_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_ip VARCHAR(255) NOT NULL,
    input_text TEXT NOT NULL,
    result_text TEXT NOT NULL,
    request_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    version INT NOT NULL DEFAULT 0
);

-- INSERT INTO translation_requests (client_ip, input_text, result_text, version)
-- VALUES
--     ('192.168.1.1', 'Hello world', 'Привет мир', 0),
--     ('192.168.1.2', 'Good morning', 'Доброе утро', 0),
--     ('192.168.1.3', 'How are you', 'Как дела', 0);

