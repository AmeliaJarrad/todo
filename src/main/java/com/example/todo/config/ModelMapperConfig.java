package com.example.todo.config;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.typeMap(String.class, String.class).setConverter(new StringTrimConverter());
        return modelMapper;
    }

    private class StringTrimConverter implements Converter<String, String> {

        @Override
        public String convert(MappingContext<String, String> context) {
            if (context.getSource() == null) {
                return null;
            }
            return context.getSource().trim();
        }

    }
}
