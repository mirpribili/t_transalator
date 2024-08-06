#!/bin/bash
# sudo apt-get install jq

# Установите количество повторений
n=35

# Ожидаемый результат
expected_response="Здравствуйте, мир!"

# URL и заголовки
url="http://localhost:8080/api/v1/translate"
headers="Content-Type: application/json"
data='{
  "sourceLang": "en",
  "targetLang": "ru",
  "text": "Hello, world!"
}'

# Функция для выполнения запроса и проверки ответа
check_translation() {
  response=$(curl -s -X POST "$url" -H "$headers" -d "$data")

  if [ "$response" = "$expected_response" ]; then
    echo "Response matches expected result: $response"
    return 0
  else
    echo "Response does not match expected result: $response"
    return 1
  fi
}

# Выполнение запроса n раз
i=1
while [ $i -le $n ]
do
  echo "Attempt $i:"
  if ! check_translation; then
    echo "Test failed on attempt $i"
    exit 1
  fi
  i=$((i + 1))
done

echo "All $n attempts matched the expected result"


curl -X GET "http://localhost:8080/api/translation-requests" -H "Content-Type: application/json" | jq
