package com.thangtq.convo.repository;

import java.util.UUID;

import com.thangtq.convo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

}
