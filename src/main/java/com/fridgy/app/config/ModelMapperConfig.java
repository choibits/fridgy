package com.fridgy.app.config;

import com.fridgy.app.dto.GroceryListRequestDto;
import com.fridgy.app.dto.RefrigeratorRequestDto;
import com.fridgy.app.model.GroceryList;
import com.fridgy.app.dto.ItemRequestDto;
import com.fridgy.app.model.Item;
import com.fridgy.app.model.Refrigerator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Skip mapping for the GroceryList id field
        modelMapper.createTypeMap(GroceryListRequestDto.class, GroceryList.class)
                .addMappings(mapper -> mapper.skip(GroceryList::setId));

        // Skip setting ID for Item
        modelMapper.createTypeMap(ItemRequestDto.class, Item.class)
                .addMappings(mapper -> mapper.skip(Item::setId));

        // Skip setting ID for Refrigerator
        modelMapper.createTypeMap(RefrigeratorRequestDto.class, Refrigerator.class)
                .addMappings(mapper -> mapper.skip(Refrigerator::setId));

        return modelMapper;
    }
}
