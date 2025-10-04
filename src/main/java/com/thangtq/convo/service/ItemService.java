package com.thangtq.convo.service;

import com.thangtq.convo.model.CreateItemRequest;
import com.thangtq.convo.model.GetItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    List<GetItemResponse> findAll();
    void insert(CreateItemRequest request);
    ResponseEntity<?> uploadFilePdf(MultipartFile file);
}
