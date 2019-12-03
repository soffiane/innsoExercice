package com.innso.serviceClient.service;

import com.innso.serviceClient.entities.DossierClient;
import com.innso.serviceClient.repository.DossierClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DossierClientService {
    private final DossierClientRepository dossierClientRepository;

    public List<DossierClient> findAll() {
        return dossierClientRepository.findAll();
    }

    public Optional<DossierClient> findById(Long id) {
        return dossierClientRepository.findById(id);
    }

    public DossierClient save(DossierClient message) {
        return dossierClientRepository.save(message);
    }

    public void deleteById(Long id) {
        dossierClientRepository.deleteById(id);
    }

    public Optional<DossierClient> findByReference(String reference) {
        return dossierClientRepository.findByReference(reference);
    }
}
