package team.aiprobe.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AiProServiceTest {

    @Autowired
    private AiProService aiProService;



    @Test
    public void testGetAssistantMsg() throws Exception {
        // Given
        String userMsg = "안녕 만나서 반가워 너는 누구니?";

        // When
        ResponseEntity<?> responseEntity = aiProService.getAssistantMsg(userMsg);

        // Then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        String responseBody = (String) responseEntity.getBody();
        assertThat(responseBody).isNotNull();
        System.out.println("\n************************************************************************");
        System.out.println("AI Pro : " + responseBody);
        System.out.println("************************************************************************\n");
    }
}