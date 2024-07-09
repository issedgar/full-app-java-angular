package org.app.ehcp.service.interfaces;

import org.app.ehcp.domain.Store;
import org.app.ehcp.domain.dto.StoreDTO;
import org.app.ehcp.domain.dto.StoreUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreService {
    Store get(Long id);
    Store create(StoreDTO storeDTO);
    Store update(Long id, StoreUpdateDTO storeUpdateDTO);
    void delete(Long id);
    Page<Store> findByPage(Pageable pageable);
}
