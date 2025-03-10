package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.MessageRequest;
import com.ducminh.blogapi.dto.response.ApiResponse;
import com.ducminh.blogapi.dto.response.MessageResponse;
import com.ducminh.blogapi.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    ApiResponse<List<MessageResponse>> getMessagesPagination(
            @RequestParam Instant createAt,
            @RequestParam String senderId,
            @RequestParam String recipentId
    ) {
        List<MessageResponse> messageResponses = messageService.getMessagesPagination(senderId, recipentId, createAt);

        ApiResponse<List<MessageResponse>> apiResponse = ApiResponse.<List<MessageResponse>>builder()
                .data(messageResponses)
                .build();
        return apiResponse;
    }
}
