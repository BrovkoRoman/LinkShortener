package org.example.service;

import org.example.database.Database;
import org.example.exception.UnknownShortLinkException;

import java.util.Random;

public class MainServiceImpl implements MainService {
    public static final int shortLinkLength = 5;
    public static final String shortLinksDomain = "localhost:8080/";

    char getSymbol(int code) {
        if(code < 26)
            return (char)(code + 'a');
        else if(code < 52)
            return (char)(code - 26 + 'A');
        else return (char)(code - 52 + '0');
    }
    @Override
    public String getShortLink(String longLink) {
        if(Database.getInstance().containsLongLink(longLink))
            return shortLinksDomain + Database.getInstance().getShortCode(longLink);

        String shortLink = null;
        Random random = new Random();

        while(shortLink == null || Database.getInstance().containsShortCode(shortLink)) {
            shortLink = "";

            for(int i = 0; i < shortLinkLength; i++)
                shortLink += getSymbol(random.nextInt(62));
        }

        Database.getInstance().addPairOfLinks(longLink, shortLink);

        return shortLinksDomain + shortLink;
    }

    @Override
    public String getLongLink(String shortLink) throws UnknownShortLinkException {
        shortLink = shortLink.substring(shortLinksDomain.length(), shortLink.length());
        if(!Database.getInstance().containsShortCode(shortLink))
            throw new UnknownShortLinkException("Короткая ссылка не найдена");
        else return Database.getInstance().getLongLink(shortLink);
    }
}
