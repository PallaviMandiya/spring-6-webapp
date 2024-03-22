package com.springframework.spring6webapp.repository;

import com.springframework.spring6webapp.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublisherRepository extends CrudRepository<Publisher,Long> {

    List<Publisher> findByPublisherNameIsLikeIgnoreCase(String s);
}
