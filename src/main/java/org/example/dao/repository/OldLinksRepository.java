package org.example.dao.repository;

import org.example.dao.entity.LinksEntity;
import org.example.exception.BadRepositoryFunctionCallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OldLinksRepository {
    @Autowired
    private final LinksRepository linksRepository;

    public OldLinksRepository(LinksRepository linksRepository) { this.linksRepository = linksRepository; }

    public String getShortCode(String longLink) throws BadRepositoryFunctionCallException {
        List<String> shortCodes = linksRepository.findShortCodesByLongLink(longLink);

        if (!shortCodes.isEmpty())
            return shortCodes.get(0);

        throw new BadRepositoryFunctionCallException("Error: long link is not found");
    }

    public String getLongLink(String shortCode) throws BadRepositoryFunctionCallException {
        List<String> longLinks = linksRepository.findLongLinksByShortCode(shortCode);

        if(!longLinks.isEmpty())
            return longLinks.get(0);

        throw new BadRepositoryFunctionCallException("Error: short code is not found");
    }

    public boolean containsShortCode(String shortCode) {
        List<String> longLinks = linksRepository.findLongLinksByShortCode(shortCode);
        return !longLinks.isEmpty();
    }

    public boolean containsLongLink(String longLink) {
        List<String> shortCodes = linksRepository.findShortCodesByLongLink(longLink);
        return !shortCodes.isEmpty();
    }

    public void addPairOfLinks(String longLink, String shortCode) {
        linksRepository.save(new LinksEntity(shortCode, longLink));
    }
}
