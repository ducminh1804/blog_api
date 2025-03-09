package com.ducminh.blogapi.controller;

import com.ducminh.blogapi.dto.request.MessageRequest;
import com.ducminh.blogapi.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public void testxongxoa(@RequestBody MessageRequest request) {
        messageService.saveMessagesToRedis(request);
    }

    @GetMapping
    public void testxongxoa1() {
        messageService.batchService();
    }

}
