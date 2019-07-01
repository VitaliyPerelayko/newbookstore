package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.Publisher;
import com.intexsoft.service.PublisherService;
import com.intexsoft.web.dto.PublisherDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for Publisher entity
 */
@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final static Logger LOGGER = LogManager.getLogger(PublisherController.class);
    private final PublisherService publisherService;
    private final Mapper mapper;

    public PublisherController(PublisherService publisherService, Mapper mapper) {
        this.publisherService = publisherService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PublisherDTO>> getAll() {
        List<PublisherDTO> publisherDTOList = publisherService.findAll().stream().map(publisher ->
                mapper.map(publisher, PublisherDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(publisherDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getOne(@PathVariable Long id) {
        PublisherDTO publisherDTO = mapper.map(publisherService.findById(id), PublisherDTO.class);
        return ResponseEntity.ok(publisherDTO);
    }

    @PostMapping
    public ResponseEntity<PublisherDTO> save(@Valid @RequestBody PublisherDTO publisherDTO) {
        PublisherDTO publisherResponseDTO = mapper.map(publisherService.save(
                mapper.map(publisherDTO, Publisher.class)
        ), PublisherDTO.class);
        return ResponseEntity.ok(publisherResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> update(@PathVariable Long id, @Valid @RequestBody PublisherDTO publisherDTO) {
        if (!id.equals(publisherDTO.getId())) {
            RuntimeException e = new RuntimeException("Id in URL path and id in request body must be the same");
            LOGGER.error(e);
            throw e;
        }
        PublisherDTO publisherResponseDTO = mapper.map(publisherService.update(
                mapper.map(publisherDTO, Publisher.class)
        ), PublisherDTO.class);
        return ResponseEntity.ok(publisherResponseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        publisherService.deleteById(id);
    }
}
