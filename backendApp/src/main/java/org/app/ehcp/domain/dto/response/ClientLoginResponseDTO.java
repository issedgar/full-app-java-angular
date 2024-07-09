package org.app.ehcp.domain.dto.response;

import lombok.Data;
import org.app.ehcp.domain.Client;
import org.springframework.beans.BeanUtils;

@Data
public class ClientLoginResponseDTO {
    private Long clientId;
    private String username;
    private String fullName;
    private String token;

    public static ClientLoginResponseDTO convertToClientLoginDTO(Client client) {
        if(client == null) {
            return null;
        }
        ClientLoginResponseDTO clientLoginResponseDTO = new ClientLoginResponseDTO();
        BeanUtils.copyProperties(client, clientLoginResponseDTO);
        return clientLoginResponseDTO;
    }
}
