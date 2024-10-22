package com.iuh.service;


import com.iuh.dto.request.PublisherRequest;
import com.iuh.entity.Publisher;

import java.util.List;

public interface PublisherService {
    Publisher save(PublisherRequest request);

    List<Publisher> findAll();

    Publisher findById(String id);

    Publisher update(String id, PublisherRequest request);

    void delete(String id);
}
