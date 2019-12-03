package com.innso.serviceClient.controller;

import com.innso.serviceClient.entities.DossierClient;
import com.innso.serviceClient.entities.Message;
import com.innso.serviceClient.exception.CustomErrorType;
import com.innso.serviceClient.service.DossierClientService;
import com.innso.serviceClient.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Un controller REST permettant d'intercepter les requetes HTTP de l'application
 * pour la gestion des messages
 *
 * @author soffiane boudissa
 */
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private DossierClientService dossierClientService;

    /**
     * Methode de creation de message
     *
     * @param message the message
     * @return le message crée
     */
    @PostMapping("")
    public ResponseEntity creerMessage(@Valid @RequestBody Message message) {
        if (message == null) {
            return ResponseEntity.badRequest().body("Les champs du message doivent etre renseignés");
        }
        //on empeche de mettre des messages en double
        Optional<Message> messageExistant = messageService.findByNameAndContent(message.getAuteur(), message.getMessage());
        if (messageExistant.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Il existe deja un message ecrit par " +
                    message.getAuteur() + " contenant le message : " + message.getMessage()), HttpStatus.CONFLICT);
        }
        return new ResponseEntity(messageService.save(message),HttpStatus.CREATED);
    }

    /**
     * Methode de creation de message et ajout dans un dossier client
     *
     * @param message the message
     * @return le dossier en cours avec le message crée
     */
    @PostMapping("/{idDossier}")
    public ResponseEntity creerMessage(@PathVariable Long idDossier, @Valid @RequestBody Message message) {
        if (message == null) {
            return ResponseEntity.badRequest().body("Les champs du message doivent etre renseignés");
        }
        //on empeche de mettre des messages en double
        Optional<Message> messageExistant = messageService.findByNameAndContent(message.getAuteur(), message.getMessage());
        if (messageExistant.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Il existe deja un message ecrit par " +
                    message.getAuteur() + " contenant le message : " + message.getMessage()), HttpStatus.CONFLICT);
        }
        //on verifie que le dossier client dans lequel on veut ajouter le message existe
        Optional<DossierClient> dossierClientExistant = dossierClientService.findById(idDossier);
        if (!dossierClientExistant.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Il n'existe pas de dossier client avec l'id " +
                    idDossier + " dans lequel ajouter le message : " + message.getMessage()), HttpStatus.NOT_FOUND);
        }

        DossierClient dossierClient = dossierClientExistant.get();
        dossierClient.addMessage(message);
        messageService.save(message);
        return new ResponseEntity(dossierClientService.save(dossierClient),HttpStatus.CREATED);
    }

    /**
     * Modifier un message.
     *
     * @param id      id du message
     * @param message le message à modifier
     * @return le message modifié
     */
    @PutMapping("/{id}")
    public ResponseEntity<Message> modifierMessage(@PathVariable Long id, @Valid @RequestBody Message message) {
        if (!messageService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(messageService.save(message));
    }

    /**
     * Supprimer un message.
     *
     * @param id id du message a supprimer
     * @return reponse http
     */
    @DeleteMapping("/{id}")
    public ResponseEntity supprimerMessage(@PathVariable Long id) {
        if (!messageService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        messageService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Recupere la liste de tous les messages.
     *
     * @return liste des messages
     */
    @GetMapping("")
    public ResponseEntity<List<Message>> findAll() {
        return ResponseEntity.ok(messageService.findAll());
    }

    /**
     * Renvoie le message dont l'id est id
     *
     * @param id id du message
     * @return le message trouvé
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> findMessage(@PathVariable Long id) {
        Optional<Message> message = messageService.findById(id);
        if (!message.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(message.get());
    }

}
