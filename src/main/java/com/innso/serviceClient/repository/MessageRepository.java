package com.innso.serviceClient.repository;

import com.innso.serviceClient.entities.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * interface Message repository.
 * @author soffiane boudissa
 */
public interface MessageRepository extends CrudRepository<Message,Long> {
    @Query("from Message m where m.auteur=?1 and m.message=?2")
    Optional<Message> findByNameAndContent(String auteur, String message);

    @Override
    List<Message> findAll();
}
