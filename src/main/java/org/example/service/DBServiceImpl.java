package org.example.service;

import org.example.dao.entity.LinksEntity;
import org.example.dao.repository.LinksRepository;
import org.example.exception.BadRepositoryFunctionCallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBServiceImpl implements DBService {
    @Autowired
    private final LinksRepository linksRepository;

    public DBServiceImpl(LinksRepository linksRepository) { this.linksRepository = linksRepository; }

    @Override
    public String getShortCodeFromDB(String longLink) {
        List<String> shortCodes = linksRepository.findShortCodesByLongLink(longLink);

        if (!shortCodes.isEmpty()) {
            return shortCodes.get(0);
        }

        throw new BadRepositoryFunctionCallException("Error: long link is not found");
    }

    @Override
    public String getLongLinkFromDB(String shortCode) {
        List<String> longLinks = linksRepository.findLongLinksByShortCode(shortCode);

        if(!longLinks.isEmpty()) {
            return longLinks.get(0);
        }

        throw new BadRepositoryFunctionCallException("Error: short code is not found");
    }

    @Override
    public boolean containsShortCode(String shortCode) {
        List<String> longLinks = linksRepository.findLongLinksByShortCode(shortCode);
        return !longLinks.isEmpty();
    }

    @Override
    public boolean containsLongLink(String longLink) {
        List<String> shortCodes = linksRepository.findShortCodesByLongLink(longLink);
        return !shortCodes.isEmpty();
    }

    @Override
    public void addPairOfLinks(String longLink, String shortCode) {
        linksRepository.save(new LinksEntity(shortCode, longLink));
    }
}
