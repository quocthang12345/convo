package com.thangtq.convo.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import com.thangtq.convo.entity.Item;
import com.thangtq.convo.feign.FileFeign;
import com.thangtq.convo.model.CreateItemRequest;
import com.thangtq.convo.model.GetItemResponse;
import com.thangtq.convo.repository.ItemRepository;
import com.thangtq.convo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
  private final ItemRepository itemRepository;
  private final FileFeign fileFeign;

  @Override
  public List<GetItemResponse> findAll() {
    return itemRepository.findAll().stream().map(toItemResponse()).toList();
  }

  @Override
  public void insert(CreateItemRequest request) {
    Item item =
        Item.builder()
            .id(UUID.randomUUID())
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .build();

    itemRepository.save(item);
  }

  @Override
  public ResponseEntity<?> uploadFilePdf(MultipartFile file) {
    return this.fileFeign.upload(file);
  }

  private Function<Item, GetItemResponse> toItemResponse() {
    return item ->
        GetItemResponse.builder()
            .id(item.getId())
            .name(item.getName())
            .description(item.getDescription())
            .price(item.getPrice())
            .createdAt(item.getCreatedAt())
            .updatedAt(item.getUpdatedAt())
            .createdBy(item.getCreatedBy())
            .updatedBy(item.getUpdatedBy())
            .build();
  }
}
