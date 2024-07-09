package org.app.ehcp.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ClientLoginDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
