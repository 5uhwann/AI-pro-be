package team.aiprobe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

import java.util.*;


@Service
@Component
public class AiProService {
    @Value("${openai.api.key}")
    private String apiKey;
    public JsonNode callChatGpt(String userMsg) throws JsonProcessingException {
        final String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model","ft:gpt-3.5-turbo-0125:ai-pro:aiprodemo:9LNhwVC3");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userMsg);
        messages.add(userMessage);

        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "system");
        assistantMessage.put("content", "aiPro는 코드 리뷰 챗봇입니다. 사용자가 준 코드를 보고 틀린 부분이나 코드 컨벤션에 어긋난 문법을 지적하고, 코드를 후술할 규칙에 맞게 리팩토링 해줍니다. aiPro가 지적할 코드 컨벤션의 내용은 다음과 같습니다. 1. Scanner를 사용 했다면 그 대신 BufferedReader 사용하도록 조언 해 줄 것. 2. 클래스 import할 때 * 가 사용 되었다면 지적하고 필요한 것만 import 시킬 것. 3. if문 , for문 중괄호 생략 시 중괄호를 필수적으로 추가하도록 지적 해 줄 것 4. 한 줄에 여러 문장 사용 시 문장 분리를 할 것 즉 ;을 사용해서 끝난 문장 일 시 줄 바꿈을 할 것 5. long type 값 뒤에 ‘L’ prefix 가 없을 시 지적 하고 추가 해 줄 것. 위에서 언급한 내용이 동시에 여러개 발생 할 시 모든 부분에 대해 지적 해주어야 한다.");
        messages.add(assistantMessage);

        bodyMap.put("messages", messages);

        String body = objectMapper.writeValueAsString(bodyMap);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        return objectMapper.readTree(response.getBody());
    }
    public ResponseEntity<?> getAssistantMsg(String userMsg) throws JsonProcessingException {
        JsonNode jsonNode = callChatGpt(userMsg);
        String content = jsonNode.path("choices").get(0).path("message").path("content").asText();

        return ResponseEntity.status(HttpStatus.OK).body(content);
    }
}
