package com.intexsoft.configuration;

import com.intexsoft.dao.model.Author;
import com.intexsoft.dao.model.Book;
import com.intexsoft.service.importdata.pojo.AuthorPOJO;
import com.intexsoft.service.importdata.pojo.BookPOJO;
import com.intexsoft.web.dto.AuthorDTO;
import com.intexsoft.web.dto.request.BookRequestDTO;
import com.intexsoft.web.dto.response.BookResponseDTO;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configuration
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {
    /**
     * @return Dozer mapper
     */
    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }
}
