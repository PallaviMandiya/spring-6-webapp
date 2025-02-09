package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.domain.Publisher;
import com.springframework.spring6webapp.dtos.PublisherDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    Iterable<PublisherDTO> getAllPublisher();

    void savePublisher(PublisherDTO publisher);

    boolean updatePublisher(Long id, PublisherDTO publisher);

    boolean deletePublisher(Long id);

    Optional<PublisherDTO> getPublisherById(Long id);

    boolean patchPublisher(Long id, PublisherDTO publisher);

    List<PublisherDTO> getPublisherByName(String publisherName);

    Page<PublisherDTO> publisherList(String publisherName, String city, String state, Integer pageNumber, Integer pageSize);
}
