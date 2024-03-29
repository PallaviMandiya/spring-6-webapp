package com.springframework.spring6webapp.repository;

import com.springframework.spring6webapp.domain.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {

    List<Publisher> findByPublisherNameIsLikeIgnoreCase(String s);

    Page<Publisher> findAllByStateIsLikeIgnoreCase(String state, Pageable pageable);

    Page<Publisher> findAllByCityIsLikeIgnoreCase(String city, Pageable pageable);

    Page<Publisher> findAllByPublisherNameIsLikeIgnoreCase(String publisherName, Pageable pageable);
}
