package com.innso.serviceClient.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.innso.serviceClient.validator.EnumValidator;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * L'Objet Message.
 * @author soffiane boudissa
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Message {
    @Id
    @GeneratedValue
    private long id;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    @CreationTimestamp
    private Date dateMessage;

    @Column
    @NotNull
    private String auteur;

    @Column
    @NotNull
    private String message;

    @Column
    @NotNull
    @NotEmpty(message = "le canal doit etre renseigné")
    @EnumValidator(enumClazz = Canal.class)
    private String canal;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="idDossier")
    @JsonBackReference
    private DossierClient idDossier;
}
