package com.innso.serviceClient.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.innso.serviceClient.validator.EnumValidator;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

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
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    @Past
    private Date dateMessage;

    @Column
    @NotNull
    private String auteur;

    @Column
    @NotNull
    private String message;

    @Column
    @NotNull
    @NotEmpty(message = "canal should not be null")
    @EnumValidator(enumClazz = Canal.class)
    private String canal;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="idDossier")
    @JsonBackReference
    private DossierClient idDossier;
}
