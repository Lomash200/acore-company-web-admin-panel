package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.model.ChatQuery;
import com.acoreweb.acoreapi.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Public endpoint (no auth)
    @PostMapping("/send")
    public ResponseEntity<?> handleChat(@RequestParam String query) {

        // 400 - empty query
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Query cannot be empty.");
        }

        // 403 - length limit (500)
        if (query.length() > 500) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Query is too long. Please keep it under 500 characters.");
        }

        // 200 - normal flow
        ChatQuery saved = chatService.saveQuery(query);
        return ResponseEntity.ok(saved);
    }

    // Optional: GET support for quick testing (browser/Postman)
    @GetMapping("/send")
    public ResponseEntity<?> handleChatGet(@RequestParam String query) {
        return handleChat(query);
    }
}
