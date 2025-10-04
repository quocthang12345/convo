package com.thangtq.convo.controller;

import com.thangtq.convo.model.CreateItemRequest;
import com.thangtq.convo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;

  @GetMapping("/items")
  public ResponseEntity<?> getItems() {
    return ResponseEntity.ok(itemService.findAll());
  }

  @PostMapping("/item")
  public ResponseEntity<?> postItem(@RequestBody CreateItemRequest request) {
    itemService.insert(request);
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/upload-pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file) {
    return itemService.uploadFilePdf(file);
  }
}
