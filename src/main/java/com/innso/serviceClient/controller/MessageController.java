package com.innso.serviceClient.controller;

import com.innso.serviceClient.entities.DossierClient;
import com.innso.serviceClient.entities.Message;
import com.innso.serviceClient.repository.DossierClientRepository;
import com.innso.serviceClient.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DossierClientRepository dossierClientRepositor;

    @PostMapping("/")
    public ResponseEntity createMessage(@Valid @RequestBody Message message){
        if(message == null){
            return ResponseEntity.badRequest().body("Cannot create message with empty fields");
        }

        //On va créer un dossierClient avec ce message
        DossierClient dossierClient = new DossierClient();
        dossierClient.addMessage(message);
        dossierClient.setNomClient(message.getAuteur());
        dossierClient.setDateOuverture(new Date());
        messageRepository.save(message);
        dossierClientRepositor.save(dossierClient);
        return new ResponseEntity(dossierClient,HttpStatus.CREATED);
    }

}
