package com.innso.serviceClient.exception;

/**
 * Exception controllée pour gerer les erreurs de creation de message.
 * @author soffiane boudissa
 */
public class MessageCreationException extends RuntimeException {
    /**
     * Instancie un objet MessageCreationException
     *
     * @param message le message d'erreur
     */
    public MessageCreationException(String message) {
        super(message);
    }
}
