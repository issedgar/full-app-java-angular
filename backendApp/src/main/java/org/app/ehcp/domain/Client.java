package org.app.ehcp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clients", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    private String lastName;
    private String address;

    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
}
