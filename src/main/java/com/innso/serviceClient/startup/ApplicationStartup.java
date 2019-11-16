package com.innso.serviceClient.startup;

import com.innso.serviceClient.exception.MessageCreationException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Scenario demandé va s'executer au demarrage de l'application SpringBoot
 * @author soffiane boudissa
 */
@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        try {
            String input = "{\n" +
                    "\t\"auteur\" : \"Jérémie Durand\",\n" +
                    "\t\"message\" : \"Bonjour, j’ai un problème avec mon nouveau téléphone\",\n" +
                    "\t\"canal\" : \"MAIL\"\n" +
                    "}";
            String etape1 = "Execution du scenario de service client en cours, message initial et creation dossier client ....";
            launchHttpMethod("POST",input,etape1);

            //deuxieme partie du scenario
            String inputBis = "{\n" +
                    "\t\"auteur\" : \"Sonia Valentin\",\n" +
                    "\t\"message\" : \"Je suis Sonia, et je vais mettre tout en œuvre pour vous aider. Quel est le modèle de votre téléphone ?\",\n" +
                    "\t\"canal\" : \"MAIL\"\n" +
                    "}";
            String etape2 = "Ajout du message du service client et mise à jour reference dossier ....";
            launchHttpMethod("POST",inputBis,etape1);

            String etape3 = "Affichage liste dossiers en cours ....";
            launchHttpMethod("GET","",etape3);

        } catch (IOException e ) {
            e.printStackTrace();
        }

    }

    /**
     * Methode permettant de lancer une requete HTTP
     *
     * @param httpMethod methode http
     * @param input      JSON en entrée
     * @param etape      description de l'etape du scenario
     * @throws IOException exception
     */
    public void launchHttpMethod(String httpMethod, String input, String etape) throws IOException {
        URL url = new URL("http://localhost:8080/api/v1/message");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(httpMethod);
        conn.setRequestProperty("Content-type", "application/json");

        if("POST".equals(httpMethod)) {
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
        }

        if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new MessageCreationException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println(etape);
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }
        conn.disconnect();
    }

}