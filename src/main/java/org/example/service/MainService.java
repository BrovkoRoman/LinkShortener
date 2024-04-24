package org.example.service;

import org.example.exception.UnknownShortLinkException;

import java.sql.SQLException;

public interface MainService {
    String getShortLink(String longLink);
    String getLongLink(String shortLink) throws UnknownShortLinkException;
}
