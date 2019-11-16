package com.innso.serviceClient.controller;

import com.innso.serviceClient.entities.DossierClient;
import com.innso.serviceClient.entities.Message;
import com.innso.serviceClient.exception.CustomErrorType;
import com.innso.serviceClient.repository.DossierClientRepository;
import com.innso.serviceClient.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Un controller REST permettant d'intercepter les requetes HTTP de l'application
 * @author soffiane boudissa
 */
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DossierClientRepository dossierClientRepository;

    /**
     * Methode de creation de message et d'un dossier client associé
     * Si il existe deja un dossier client, on ajoute juste le message au dossier existant
     * @param message
     * @return le dossier crée ou mis à jour
     */
    @PostMapping("")
    public ResponseEntity creerMessageEtDossierClient(@Valid @RequestBody Message message){
        DossierClient dossierClient;
        if(message == null){
            return ResponseEntity.badRequest().body("Les champs du message doivent etre renseignés");
        }
        //on empeche de mettre des messages en double
        Optional<Message> messageExistant = messageRepository.findByNameAndContent(message.getAuteur(), message.getMessage());
        if(messageExistant.isPresent()){
            return new ResponseEntity(new CustomErrorType("Il existe deja un message ecrit par " +
                    message.getAuteur() + " contenant le message : "+message.getMessage()), HttpStatus.CONFLICT);
        }
        //on verifie si on a un dossier client existant a ce nom ou pas
        final List<DossierClient> dossierClientExistant = dossierClientRepository.findAll();
        //si dossierClient deja crée,on ajoute juste le message au dossier
        if(!dossierClientExistant.isEmpty()){
            dossierClient = dossierClientExistant.get(0);
            dossierClient.addMessage(message);
            //il faut verifier si le dossierClient a un code reference ou non
            if(StringUtils.isEmpty(dossierClient.getReference())){
                dossierClient.setReference("KA-18B6");
            }
        } else {
            //si le dossier client n'existe pas, on va le créer et ajouter le message dedans
            dossierClient = new DossierClient();
            dossierClient.addMessage(message);
            dossierClient.setNomClient(message.getAuteur());
            dossierClient.setDateOuverture(new Date());
        }
        messageRepository.save(message);
        dossierClientRepository.save(dossierClient);
        return new ResponseEntity(dossierClient,HttpStatus.CREATED);
    }

    /**
     * Méthode permettant de recuperer la liste des dossiers client en cours
     * @return la liste des dossiers client
     */
    @GetMapping("")
    public ResponseEntity getDossiersClient(){
        List<DossierClient> allDossierClient = dossierClientRepository.findAll();
        if(allDossierClient.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allDossierClient);
    }

}
