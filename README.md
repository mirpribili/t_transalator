# Translator Application

Проект для перевода текста с использованием Yandex Translate API. Приложение обрабатывает перевод каждого слова в многопоточном режиме с максимальным количеством одновременно выполняемых потоков - 10, сохраняет информацию о запросах в реляционную базу данных с использованием JDBC и осуществляет внешние системные вызовы через RestTemplate.

## Требования

- Java 17
- Maven 3.6+
- Spring Boot 3.3.2
- H2 Database
- Yandex Translate API ключ

## Установка
###  Клонируйте репозиторий:
```bash
git clone https://github.com/mirpribili/t_transalator
cd t_transalator
```

###  Соберите проект:
`mvn clean install`
###  Использование: 
- Перевод текста
- Отправьте POST-запрос на /api/v1/translate с JSON телом запроса:
```
json
Копировать код
{
"sourceLang": "en",
"targetLang": "ru",
"text": "Hello world"
}
```
### Пример запроса с использованием curl:
```
url="http://localhost:8080/api/v1/translate"
headers="Content-Type: application/json"
data='{
  "sourceLang": "en",
  "targetLang": "ru",
  "text": "Hello, world!"
}'
```
### Получение всех запросов на перевод
Отправьте GET-запрос на /api/translation-requests для получения всех сохраненных запросов:
```
curl -X GET "http://localhost:8080/api/translation-requests" -H "Content-Type: application/json"
```
### Тестирование
- Для запуска тестов выполните:
`mvn test`
### Структура проекта
- com.service.translator: основной пакет приложения.
- controller: содержит контроллеры для обработки HTTP-запросов.
- service: содержит бизнес-логику приложения.
- repository: содержит методы для работы с базой данных.
- config: содержит конфигурационные классы.
- dto: содержит классы для передачи данных.
- model: содержит модели данных.
- exception: содержит классы исключений.
- integration: содержит интеграционные тесты.
- tests: содержит тесты.
