package org.example.service;

import org.example.dao.entity.LinksEntity;
import org.example.dao.repository.LinksRepository;
import org.example.exception.BadRepositoryFunctionCallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DBServiceImpl implements DBService {
    @Autowired
    private final LinksRepository linksRepository;

    public DBServiceImpl(LinksRepository linksRepository) { this.linksRepository = linksRepository; }

    @Override
    @Cacheable(value = "DBServiceImpl::getShortCodeFromDB", key = "#longLink")
    public String getShortCodeFromDB(String longLink) {
        System.out.println("getShortCodeFromDB");
        List<String> shortCodes = linksRepository.findShortCodesByLongLink(longLink);

        if (!shortCodes.isEmpty()) {
            return shortCodes.get(0);
        }

        throw new BadRepositoryFunctionCallException("Error: long link is not found");
    }

    @Override
    @Cacheable(value = "DBServiceImpl::getLongLinkFromDB", key = "#shortCode")
    public String getLongLinkFromDB(String shortCode) {
        System.out.println("getLongLinkFromDB");
        List<String> longLinks = linksRepository.findLongLinksByShortCode(shortCode);

        if(!longLinks.isEmpty()) {
            return longLinks.get(0);
        }

        throw new BadRepositoryFunctionCallException("Error: short code is not found");
    }

    @Override
    @Cacheable(value = "DBServiceImpl::containsShortCode", key = "#shortCode")
    public boolean containsShortCode(String shortCode) {
        System.out.println("containsShortCode");
        List<String> longLinks = linksRepository.findLongLinksByShortCode(shortCode);
        return !longLinks.isEmpty();
    }

    @Override
    @Cacheable(value = "DBServiceImpl::containsLongLink", key = "#longLink")
    public boolean containsLongLink(String longLink) {
        System.out.println("containsLongLink");
        List<String> shortCodes = linksRepository.findShortCodesByLongLink(longLink);
        return !shortCodes.isEmpty();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "DBServiceImpl::containsLongLink", key = "#longLink"),
            @CacheEvict(value = "DBServiceImpl::containsShortCode", key = "#shortCode")
    })
    public void addPairOfLinks(String longLink, String shortCode) {
        System.out.println("addPairOfLinks");
        linksRepository.save(new LinksEntity(shortCode, longLink));
    }
}
