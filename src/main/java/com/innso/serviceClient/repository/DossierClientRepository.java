package com.innso.serviceClient.repository;

import com.innso.serviceClient.entities.DossierClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * interface Dossier client repository.
 * @author soffiane boudissa
 */
public interface DossierClientRepository extends CrudRepository<DossierClient,Long> {
    @Override
    List<DossierClient> findAll();
}
