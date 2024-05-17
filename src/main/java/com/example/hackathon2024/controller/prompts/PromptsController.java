package com.example.hackathon2024.controller.prompts;

import com.example.hackathon2024.service.prompts.PromptsTips;
import com.example.hackathon2024.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api-greenpal/")
public class PromptsController {
    private final PromptsTips tipsService;

    @GetMapping("/tip")
    public ApiResponse<String> getRandomTip() {
        return tipsService.getRandomTip();
    }
}
