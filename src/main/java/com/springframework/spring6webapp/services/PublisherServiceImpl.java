package com.springframework.spring6webapp.services;

import ch.qos.logback.core.util.DelayStrategy;
import com.springframework.spring6webapp.domain.Book;
import com.springframework.spring6webapp.domain.Publisher;
import com.springframework.spring6webapp.dtos.PublisherDTO;
import com.springframework.spring6webapp.mapper.PublisherMapper;
import com.springframework.spring6webapp.repository.BookRepository;
import com.springframework.spring6webapp.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private PublisherMapper publisherMapper;
    @Autowired
    private BookRepository bookRepository;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 5;

    @Override
    public Iterable<PublisherDTO> getAllPublisher() {
        List<PublisherDTO> publisherDTOS = new ArrayList<>();
        for(Publisher publisher : publisherRepository.findAll()){
            publisherDTOS.add(publisherMapper.publisherToPublisherDto(publisher));
        }
        return publisherDTOS;
    }

    @Override
    public void savePublisher(PublisherDTO publisher) {
        publisherRepository.save(publisherMapper.publisherDtoToPublisher(publisher));
    }

    @Override
    public boolean updatePublisher(Long id, PublisherDTO publisherDTO) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        if(optionalPublisher.isPresent()){
            Publisher publisher = optionalPublisher.get();

            publisher.setPublisherName(publisherDTO.getPublisherName());
            publisher.setAddress(publisherDTO.getAddress());
            publisher.setCity(publisherDTO.getCity());
            publisher.setState(publisherDTO.getState());
            publisher.setZipCode(publisherDTO.getZipCode());
            publisherRepository.save(publisher);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deletePublisher(Long id) {
        if(publisherRepository.existsById(id)){
            for(Book book: bookRepository.findByPublisherId(id)){
                book.setPublisher(null);
            }
            publisherRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<PublisherDTO> getPublisherById(Long id) {
        return Optional.ofNullable(publisherMapper.publisherToPublisherDto(publisherRepository.findById(id).orElse(null)));
//        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
//        if(optionalPublisher.isPresent()){
//            return optionalPublisher.get();
//        }else{
//            throw new NoSuchElementException("Publisher Not Found With Id: "+ id);
//        }
    }

    @Override
    public boolean patchPublisher(Long id, PublisherDTO publisherDTO) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        if(optionalPublisher.isPresent()) {
            Publisher publisher = optionalPublisher.get();

            if (StringUtils.hasText(publisherDTO.getPublisherName())) {
                publisher.setPublisherName(publisherDTO.getPublisherName());
            }
            if (StringUtils.hasText(publisherDTO.getAddress())) {
                publisher.setAddress(publisherDTO.getAddress());
            }
            if (StringUtils.hasText(publisherDTO.getCity())) {
                publisher.setCity(publisherDTO.getCity());
            }
            if (StringUtils.hasText(publisherDTO.getState())) {
                publisher.setState(publisherDTO.getState());
            }
            if (StringUtils.hasText(publisherDTO.getZipCode())) {
                publisher.setZipCode(publisherDTO.getZipCode());
            }
            publisherRepository.save(publisher);
            return true;
        }
        return false;
    }

    @Override
    public List<PublisherDTO> getPublisherByName(String publisherName) {
        List<PublisherDTO> publisherDTOS = new ArrayList<>();
        for(Publisher publisher: publisherRepository.findByPublisherNameIsLikeIgnoreCase("%" + publisherName + "%")){
            publisherDTOS.add(publisherMapper.publisherToPublisherDto(publisher));
        }
        return publisherDTOS;
    }

    @Override
    public Page<PublisherDTO> publisherList(String publisherName, String city, String state, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        Page<Publisher> publisherPage;
        if(StringUtils.hasText(publisherName)){
            publisherPage = listPublisherByPublisherName(publisherName, pageRequest);
        } else if (StringUtils.hasText(city)) {
            publisherPage = listPublisherByCity(city, pageRequest);
        } else if (StringUtils.hasText(state)) {
            publisherPage = listPublisherByState(state, pageRequest);
        } else{
            publisherPage = publisherRepository.findAll(pageRequest);
        }

        return publisherPage.map(publisherMapper::publisherToPublisherDto);
    }

    public Page<Publisher> listPublisherByState(String state, Pageable pageable) {
        return publisherRepository.findAllByStateIsLikeIgnoreCase("%" + state + "%", pageable);
    }

    public Page<Publisher> listPublisherByCity(String city, Pageable pageable) {
        return publisherRepository.findAllByCityIsLikeIgnoreCase("%" + city + "%", pageable);
    }

    public Page<Publisher> listPublisherByPublisherName(String publisherName, Pageable pageable) {
        return publisherRepository.findAllByPublisherNameIsLikeIgnoreCase("%" + publisherName + "%", pageable);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;
        if(pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber - 1;
        }else{
            queryPageNumber = DEFAULT_PAGE;
        }
        if(pageSize > 1000){
            queryPageSize = 1000;
        }else{
            queryPageSize = DEFAULT_PAGE_SIZE;
        }

        Sort sort = Sort.by(Sort.Order.asc("publisherName"));
        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }
}


