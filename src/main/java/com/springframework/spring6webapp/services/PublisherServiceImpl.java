package com.springframework.spring6webapp.services;

import com.springframework.spring6webapp.domain.Publisher;
import com.springframework.spring6webapp.dtos.PublisherDTO;
import com.springframework.spring6webapp.mapper.PublisherMapper;
import com.springframework.spring6webapp.repository.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public boolean deletePublisher(Long id) {
        if(publisherRepository.existsById(id)){
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
}


