package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.Author;
import com.intexsoft.service.enyityservice.AuthorService;
import com.intexsoft.web.dto.AuthorDTO;
import com.intexsoft.web.dto.mapping.AuthorDTOMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for entity Book.
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final static Logger LOGGER = LogManager.getLogger(AuthorController.class);
    private final AuthorService authorService;
    private final AuthorDTOMapper authorDTOMapper;

    public AuthorController(AuthorService authorService, AuthorDTOMapper authorDTOMapper) {
        this.authorService = authorService;
        this.authorDTOMapper = authorDTOMapper;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll() {
        List<Author> authors = authorService.findAll();
        return ResponseEntity.ok(authors.stream().
                map(authorDTOMapper::mapAuthorToAuthorDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getOne(@PathVariable Long id) {
        AuthorDTO authorDTO = authorDTOMapper.mapAuthorToAuthorDTO(authorService.findById(id));
        return ResponseEntity.ok(authorDTO);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> save(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO authorResponseDTO = authorDTOMapper.mapAuthorToAuthorDTO(authorService.save(
                authorDTOMapper.mapAuthorDTOToAuthor(authorDTO)));
        return ResponseEntity.ok(authorResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
        if (!id.equals(authorDTO.getId())) {
            RuntimeException e = new RuntimeException("Id in URL path and id in request body must be the same");
            LOGGER.error(e);
            throw e;
        }
        AuthorDTO authorResponseDTO = authorDTOMapper.mapAuthorToAuthorDTO(authorService.update(
                authorDTOMapper.mapAuthorDTOToAuthor(authorDTO)));
        return ResponseEntity.ok(authorResponseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        authorService.deleteById(id);
    }

}
