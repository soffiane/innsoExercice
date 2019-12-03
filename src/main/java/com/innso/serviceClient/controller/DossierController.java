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
 * pour la gestion des dossier client
 *
 * @author soffiane boudissa
 */
@RestController
@RequestMapping("/api/v1/dossier")
public class DossierController {

    @Autowired
    private DossierClientService dossierClientService;

    @Autowired
    private MessageService messageService;

    @PostMapping("")
    public ResponseEntity creerDossierClient(@Valid @RequestBody DossierClient dossierClient){
        if(dossierClient == null){
            return ResponseEntity.badRequest().body("Les champs du dossier client doivent etre renseignés");
        }
        //on verifie si on a un dossier client existant a ce nom ou pas
        Optional<DossierClient> dossierClientExistant = dossierClientService.findByReference(dossierClient.getReference());
        if (dossierClientExistant.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Il existe deja un dossier client avec comme reference " +
                    dossierClient.getReference()), HttpStatus.CONFLICT);
        }

        return new ResponseEntity(dossierClientService.save(dossierClient),HttpStatus.CREATED);
    }

    @PostMapping("/{idMessage}")
    public ResponseEntity creerDossierClient(@PathVariable Long idMessage, @Valid @RequestBody DossierClient dossierClient){
        if(dossierClient == null){
            return ResponseEntity.badRequest().body("Les champs du dossier client doivent etre renseignés");
        }
        //on verifie si on a un dossier client existant a ce nom ou pas
        Optional<DossierClient> dossierClientExistant = dossierClientService.findByReference(dossierClient.getReference());
        if (dossierClientExistant.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Il existe deja un dossier client avec comme reference " +
                    dossierClient.getReference()), HttpStatus.CONFLICT);
        }
        //On verifie que le message que l'on veut ajouter au dossier existe
        Optional<Message> messageExistant = messageService.findById(idMessage);
        if (!messageExistant.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Pas de message avec l'id " +
                    idMessage+" a ajouter au dossier client"), HttpStatus.CONFLICT);
        }
        dossierClient.addMessage(messageExistant.get());
        return new ResponseEntity(dossierClientService.save(dossierClient),HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<DossierClient>> trouverTousLesDossiersClient() {
        List<DossierClient> dossierClient = dossierClientService.findAll();
        if (dossierClient.isEmpty()) {
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dossierClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DossierClient> trouverDossierClient(@PathVariable Long id) {
        Optional<DossierClient> dossierClient = dossierClientService.findById(id);
        if (!dossierClient.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dossierClient.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DossierClient> modifierDossierClient(@PathVariable Long id, @Valid @RequestBody DossierClient dossierClient) {
        if (!dossierClientService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(dossierClientService.save(dossierClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity supprimerDossierClient(@PathVariable Long id) {
        if (!dossierClientService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        dossierClientService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
