package com.innso.serviceClient.controller;

import com.innso.serviceClient.entities.DossierClient;
import com.innso.serviceClient.entities.Message;
import com.innso.serviceClient.repository.DossierClientRepository;
import com.innso.serviceClient.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/dossier")
public class DossierClientController {

    @Autowired
    private DossierClientRepository dossierClientRepositor;

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/")
    public ResponseEntity createDossierClient(@RequestBody DossierClient dossierClient){
        //verifier que le client existe dans la base de message et le recuperer
        final Optional<Message> message = messageRepository.findByNomClient(dossierClient.getNomClient());
        if(!message.isPresent()){
            return ResponseEntity.notFound().build();
        }
        //et verifier qu'il n'existe pas deja un dossier client pur ce nom
        final Optional<DossierClient> dossierClientStored = dossierClientRepositor.findByNomClient(dossierClient.getNomClient());
        if(dossierClientStored.isPresent()){
            return new ResponseEntity("il existe deja un dossier client pour le client : "+dossierClient.getNomClient(), HttpStatus.CONFLICT);
        }
        //creer un dossier client et y mettre le message
        /*final Message messageStored = message.get();
        DossierClient dossierClientStored = dossierClientStored.get();
        messageStored.setIdDossier(dossierClientStored);
        dossierClient.addMessage(messageStored);*/
        DossierClient dossierClientCreated = dossierClientRepositor.save(dossierClient);
        return new ResponseEntity<>(dossierClientCreated,HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity getDossierClient(){
        List<DossierClient> allDossierClient = dossierClientRepositor.findAll();
        if(allDossierClient.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allDossierClient);
    }
}
