package org.app.ehcp.domain.dto;

import lombok.Data;
import org.app.ehcp.domain.Client;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Data
public class ClientCreateDTO {
    @NotNull
    private String name;
    private String lastName;
    private String address;

    @NotNull
    private String username;
    @NotNull
    private String password;

    public static Client convertToClient(ClientCreateDTO clientCreateDTO) {
        if(clientCreateDTO == null) {
            return null;
        }
        Client client = new Client();
        BeanUtils.copyProperties(clientCreateDTO, client);
        return client;
    }
}
