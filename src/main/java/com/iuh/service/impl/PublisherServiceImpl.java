package com.iuh.service.impl;

import com.iuh.dto.request.PublisherRequest;
import com.iuh.entity.Publisher;
import com.iuh.exception.AppException;
import com.iuh.exception.ErrorCode;
import com.iuh.mapper.PublisherMapper;
import com.iuh.repository.PublisherRepository;
import com.iuh.service.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PublisherServiceImpl implements PublisherService {
    PublisherRepository publisherRepository;
    PublisherMapper publisherMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Publisher save(PublisherRequest request) {
        return publisherRepository.save(publisherMapper.toEntity(request));
    }

    @Override
    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Publisher findById(String id) {
        return publisherRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PUBLISHER_NOT_FOUND));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Publisher update(String id, PublisherRequest request) {
        Publisher publisher = findById(id);
        publisherMapper.toUpdateEntity(publisher, request);
        return publisherRepository.save(publisher);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id) {
        publisherRepository.deleteById(id);
    }
}
