package com.ducminh.blogapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticsearchService {
    @Autowired
    private ElasticsearchOperations operations;

    public Object save(Object object) {
        return operations.save(object);
    }
}
