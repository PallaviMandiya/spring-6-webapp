package com.springframework.spring6webapp.mapper;

import com.springframework.spring6webapp.domain.Publisher;
import com.springframework.spring6webapp.dtos.PublisherDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PublisherMapper {
    Publisher publisherDtoToPublisher(PublisherDTO publisherDTO);
    PublisherDTO publisherToPublisherDto(Publisher publisher);

}
