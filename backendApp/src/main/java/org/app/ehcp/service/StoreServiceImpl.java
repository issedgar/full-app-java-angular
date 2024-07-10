package org.app.ehcp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.app.ehcp.domain.Store;
import org.app.ehcp.domain.dto.StoreDTO;
import org.app.ehcp.domain.dto.StoreUpdateDTO;
import org.app.ehcp.repository.StoreRepository;
import org.app.ehcp.service.interfaces.StoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final StoreRepository repository;

    public StoreServiceImpl(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Store create(StoreDTO storeDTO) {
        Optional<Store> storeOptional = repository.findByName(storeDTO.getName());
        if(storeOptional.isPresent()) {
            log.error("Store exists whit name");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store exists whit name");
        }
        try {
            Store store = StoreDTO.convertToStore(storeDTO);
            return repository.save(store);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error create");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error create");
        }
    }

    @Override
    @Transactional
    public Store update(Long id, StoreUpdateDTO storeUpdateDTO) {
        Optional<Store> storeOptional = repository.findById(id);
        if(storeOptional.isEmpty()) {
            log.error("Store not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store not found");
        }

        Store store = storeOptional.get();

        if(Strings.isNotEmpty(storeUpdateDTO.getName())) {
            store.setName(storeUpdateDTO.getName());
        }
        if(Strings.isNotEmpty(storeUpdateDTO.getAddress())) {
            store.setAddress(storeUpdateDTO.getAddress());
        }

        try {
            return repository.save(store);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error update");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error update");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Store> storeOptional = repository.findById(id);
        if(storeOptional.isEmpty()) {
            log.error("Store not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store not found");
        }
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error delete");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error delete");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Store get(Long id) {
        Optional<Store> storeOptional = repository.findById(id);
        if(storeOptional.isEmpty()) {
            log.error("Store not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store not found");
        }
        return storeOptional.get();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Store> findByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Store> findAll() {
        return repository.findAll();
    }
}
