package org.example.service;

import org.example.exception.UnknownShortLinkException;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LinkServiceImpl implements LinkService {
    public static final int shortLinkLength = 5;
    public static final String shortLinksDomain = "localhost:8080/url/";
    private final DBService dbService;

    public LinkServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    char getSymbol(int code) {
        if(code < 26)
            return (char)(code + 'a');
        else if(code < 52)
            return (char)(code - 26 + 'A');
        else return (char)(code - 52 + '0');
    }
    @Override
    public String getShortLink(String longLink) {
        if(dbService.containsLongLink(longLink))
            return shortLinksDomain + dbService.getShortCodeFromDB(longLink);

        String shortLink = null;
        Random random = new Random();

        while(shortLink == null || dbService.containsShortCode(shortLink)) {
            shortLink = "";

            for(int i = 0; i < shortLinkLength; i++)
                shortLink += getSymbol(random.nextInt(62));
        }

        dbService.addPairOfLinks(longLink, shortLink);

        return shortLinksDomain + shortLink;
    }

    @Override
    public String getLongLink(String shortLink) throws UnknownShortLinkException {
        shortLink = shortLink.substring(shortLinksDomain.length(), shortLink.length());
        if(!dbService.containsShortCode(shortLink))
            throw new UnknownShortLinkException("Короткая ссылка не найдена");
        else return dbService.getLongLinkFromDB(shortLink);
    }
}
