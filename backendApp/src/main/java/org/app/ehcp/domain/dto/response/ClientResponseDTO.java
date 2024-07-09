package org.app.ehcp.domain.dto.response;

import lombok.Data;
import org.app.ehcp.domain.Client;
import org.springframework.beans.BeanUtils;

@Data
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String lastName;
    private String address;
    private String username;

    public static ClientResponseDTO convertToClientResponseDTO(Client client) {
        if(client == null) {
            return null;
        }
        ClientResponseDTO clientCreateDTO = new ClientResponseDTO();
        BeanUtils.copyProperties(client, clientCreateDTO);
        return clientCreateDTO;
    }

}
