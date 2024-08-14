package com.altimetrik.inventory.service;

import com.altimetrik.inventory.model.Inventory;
import com.altimetrik.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private KafkaTemplate<String, Inventory> kafkaTemplate;

    private static final String TOPIC = "inventory_topic";

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory addInventory(Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        kafkaTemplate.send(TOPIC, savedInventory);
        return savedInventory;
    }

    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }
}
