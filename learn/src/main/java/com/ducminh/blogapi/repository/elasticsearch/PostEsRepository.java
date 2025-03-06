package com.ducminh.blogapi.repository.elasticsearch;


import com.ducminh.blogapi.entity.PostEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostEsRepository extends ElasticsearchRepository<PostEs, String> {
}
