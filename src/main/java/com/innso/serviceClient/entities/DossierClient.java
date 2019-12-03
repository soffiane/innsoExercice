package com.innso.serviceClient.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * L'objet Dossier client.
 * @author soffiane boudissa
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DossierClient {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String reference;

    @Column
    @NotNull
    private String nomClient;

    @Column
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date dateOuverture;

    @OneToMany(mappedBy = "idDossier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Message> messages = new HashSet<>();

    /**
     * Ajouter un message au dossier.
     *
     * @param message le message à ajouter
     */
    public void addMessage(Message message){
        message.setIdDossier(this);
        messages.add(message);
    }
}
