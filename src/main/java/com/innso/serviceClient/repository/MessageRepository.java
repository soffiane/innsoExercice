package com.innso.serviceClient.repository;

import com.innso.serviceClient.entities.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message,Long> {
    @Query("from Message m where m.auteur=?1")
    Optional<Message> findByNomClient(String nomClient);
}
