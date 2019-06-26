package com.intexsoft.web.controllers;

import com.intexsoft.dao.model.Author;
import com.intexsoft.service.AuthorService;
import com.intexsoft.web.dto.AuthorDTO;
import com.intexsoft.web.mapping.CustomMapping;
import org.dozer.Mapper;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper mapper;
    private final CustomMapping customMapping;

    public AuthorController(AuthorService authorService, Mapper mapper, CustomMapping customMapping) {
        this.authorService = authorService;
        this.mapper = mapper;
        this.customMapping = customMapping;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll() {
        List<Author> authors = authorService.findAll();
        return ResponseEntity.ok(authors.stream().
                map(customMapping::mapAuthorToAuthorDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getOne(@PathVariable Long id) {
        AuthorDTO authorDTO = customMapping.mapAuthorToAuthorDTO(authorService.findById(id));
        return ResponseEntity.ok(authorDTO);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> save(@Valid @RequestBody AuthorDTO authorDTO) {
        AuthorDTO authorResponseDTO = customMapping.mapAuthorToAuthorDTO(authorService.save(
                customMapping.mapAuthorDTOToAuthor(authorDTO)));
        return ResponseEntity.ok(authorResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
        if (!id.equals(authorDTO.getId())) {
            throw new RuntimeException("Id in URL path and id in request body must be the same");
        }
        AuthorDTO authorResponseDTO = customMapping.mapAuthorToAuthorDTO(authorService.update(
                customMapping.mapAuthorDTOToAuthor(authorDTO)));
        return ResponseEntity.ok(authorResponseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        authorService.deleteById(id);
    }

}
