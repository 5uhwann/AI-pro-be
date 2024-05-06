package team.aiprobe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.aiprobe.service.AiProService;

import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin
public class AiProController {
    private final AiProService aiProService;

    @Autowired
    public AiProController(AiProService aiProService){
        this.aiProService = aiProService;
    }

    @PostMapping("/")
    public ResponseEntity<?> getAssistantMsg(@RequestBody Map<String, String> requestBody) throws JsonProcessingException {
        String msg = requestBody.get("msg");
        return aiProService.getAssistantMsg(msg);
    }
}
