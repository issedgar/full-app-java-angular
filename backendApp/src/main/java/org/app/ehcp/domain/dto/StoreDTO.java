package org.app.ehcp.domain.dto;

import lombok.Data;
import org.app.ehcp.domain.Store;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
public class StoreDTO {
    @NotNull
    private String name;
    private String address;

    public static Store convertToStore(StoreDTO storeDTO) {
        if(storeDTO == null) {
            return null;
        }
        Store store = new Store();
        BeanUtils.copyProperties(storeDTO, store);
        return store;
    }
}
