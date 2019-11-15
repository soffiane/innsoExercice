package com.innso.serviceClient.repository;

import com.innso.serviceClient.entities.DossierClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DossierClientRepository extends CrudRepository<DossierClient,Long> {
    @Query("from DossierClient m where m.nomClient=?1")
    Optional<DossierClient> findByNomClient(String nomClient);

    @Override
    List<DossierClient> findAll();
}
