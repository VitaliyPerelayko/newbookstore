package com.intexsoft.configuration;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.web.dto.AuthorDTO;
import com.intexsoft.web.dto.request.BookRequestDTO;
import com.intexsoft.web.dto.response.BookResponseDTO;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Web Configuration
 */
@Configuration
@EnableWebMvc
public class WebConfiguration {
    /**
     * @return Dozer mapper
     */
    @Bean
    public Mapper mapper() {
        BeanMappingBuilder builder = new BeanMappingBuilder() {

            @Override
            protected void configure() {
                mapping(Author.class, AuthorDTO.class).exclude("birthDate");
                mapping(Book.class, BookRequestDTO.class).exclude("publishDate");
                mapping(Book.class, BookResponseDTO.class).exclude("publishDate");

            }
        };
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(builder);
        return mapper;
    }
}
