package com.api.client.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "cpf", nullable = false, unique = true)
  private String cpf;

  @Column(name = "phone_number", nullable = false, unique = true)
  private String phoneNumber;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "email", nullable = false, unique = true)
  private String email;
}
