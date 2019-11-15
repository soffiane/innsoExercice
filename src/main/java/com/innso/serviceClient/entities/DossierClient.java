package com.innso.serviceClient.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DossierClient {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String reference;
    @Column
    @NotNull
    private String nomClient;
    @Column
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd'T'hh:mm:ss")
    @Past
    private Date dateOuverture;

    @OneToMany(mappedBy = "idDossier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Message> messages = new HashSet<>();

    public void addMessage(Message message){
        message.setIdDossier(this);
        messages.add(message);
    }
}
