package com.innso.serviceClient.repository;

import com.innso.serviceClient.entities.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * interface Message repository.
 * @author soffiane boudissa
 */
public interface MessageRepository extends CrudRepository<Message,Long> {
}
