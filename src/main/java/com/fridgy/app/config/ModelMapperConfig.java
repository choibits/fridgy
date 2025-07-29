package com.fridgy.app.config;

import com.fridgy.app.dto.GroceryListRequestDto;
import com.fridgy.app.model.GroceryList;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(GroceryListRequestDto.class, GroceryList.class)
                .addMappings(mapper -> mapper.skip(GroceryList::setId));
        return modelMapper;
    }
}
