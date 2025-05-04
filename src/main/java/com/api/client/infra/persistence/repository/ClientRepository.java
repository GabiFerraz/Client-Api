package com.api.client.infra.persistence.repository;

import com.api.client.infra.persistence.entity.ClientEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

  Optional<ClientEntity> findByCpf(final String cpf);

  void deleteByCpf(final String cpf);
}
