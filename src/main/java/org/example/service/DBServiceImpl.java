package org.example.service;

import org.example.dao.LinksDao;
import org.example.dao.entity.LinksEntity;
import org.example.dao.repository.LinksRepository;
import org.example.exception.BadRepositoryFunctionCallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DBServiceImpl implements DBService {
    @Autowired
    private final LinksRepository linksRepository;

    public DBServiceImpl(LinksRepository linksRepository) { this.linksRepository = linksRepository; }

    @Override
    //@Cacheable(value = "DBServiceImpl::getShortCodeFromDB", key = "#longLink")
    public String getShortCodeFromDB(String longLink) {
        //System.out.println("getShortCodeFromDB");
        Optional<String[]> entity = linksRepository.findByLongLink(longLink);

        if (entity.isPresent() && entity.get().length > 0) {
            String[] links = entity.get()[0].split(",");
            linksRepository.save(new LinksEntity(links[0], links[1]));
            return links[0];
        }

        throw new BadRepositoryFunctionCallException("Error: long link is not found");
    }

    @Override
    //@Cacheable(value = "DBServiceImpl::getLongLinkFromDB", key = "#shortCode")
    public String getLongLinkFromDB(String shortCode) {
        //System.out.println("getLongLinkFromDB");
        Optional<String[]> entity = linksRepository.findByShortCode(shortCode);

        if(entity.isPresent() && entity.get().length > 0) {
            String[] links = entity.get()[0].split(",");
            linksRepository.save(new LinksEntity(links[0], links[1]));
            return links[1];
        }

        throw new BadRepositoryFunctionCallException("Error: short code is not found");
    }

    @Override
    //@Cacheable(value = "DBServiceImpl::containsShortCode", key = "#shortCode")
    public boolean containsShortCode(String shortCode) {
        //System.out.println("containsShortCode");
        Optional<String[]> entity = linksRepository.findByShortCode(shortCode);
        return entity.isPresent() && entity.get().length > 0;
    }

    @Override
    //@Cacheable(value = "DBServiceImpl::containsLongLink", key = "#longLink")
    public boolean containsLongLink(String longLink) {
        //System.out.println("containsLongLink");
        Optional<String[]> entity = linksRepository.findByLongLink(longLink);
        return entity.isPresent() && entity.get().length > 0;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "LinksRepository::findByLongLink", key = "#longLink"),
            @CacheEvict(value = "LinksRepository::findByShortCode", key = "#shortCode")
    })
    public void addPairOfLinks(String longLink, String shortCode) {
        //System.out.println("addPairOfLinks");
        linksRepository.save(new LinksEntity(shortCode, longLink));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "LinksRepository::findByLongLink", key = "#longLink"),
            @CacheEvict(value = "LinksRepository::findByShortCode", key = "#shortCode")
    })
    public void deletePairOfLinks(String longLink, String shortCode) {
        linksRepository.deleteById(shortCode);
    }
}
