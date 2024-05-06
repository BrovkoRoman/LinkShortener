package org.example.service;

import org.example.exception.BadRepositoryFunctionCallException;
import org.example.exception.UnknownShortLinkException;
import org.example.dao.repository.OldLinksRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MainServiceImpl implements MainService {
    public static final int shortLinkLength = 5;
    public static final String shortLinksDomain = "localhost:8080/";
    private final OldLinksRepository linksRepository;

    public MainServiceImpl(OldLinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    char getSymbol(int code) {
        if(code < 26)
            return (char)(code + 'a');
        else if(code < 52)
            return (char)(code - 26 + 'A');
        else return (char)(code - 52 + '0');
    }
    @Override
    public String getShortLink(String longLink) throws BadRepositoryFunctionCallException {
        if(linksRepository.containsLongLink(longLink))
            return shortLinksDomain + linksRepository.getShortCode(longLink);

        String shortLink = null;
        Random random = new Random();

        while(shortLink == null || linksRepository.containsShortCode(shortLink)) {
            shortLink = "";

            for(int i = 0; i < shortLinkLength; i++)
                shortLink += getSymbol(random.nextInt(62));
        }

        linksRepository.addPairOfLinks(longLink, shortLink);

        return shortLinksDomain + shortLink;
    }

    @Override
    public String getLongLink(String shortLink) throws UnknownShortLinkException, BadRepositoryFunctionCallException {
        shortLink = shortLink.substring(shortLinksDomain.length(), shortLink.length());
        if(!linksRepository.containsShortCode(shortLink))
            throw new UnknownShortLinkException("Короткая ссылка не найдена");
        else return linksRepository.getLongLink(shortLink);
    }
}
