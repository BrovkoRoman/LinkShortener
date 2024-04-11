package org.example.service;

import org.example.exception.UnknownShortLinkException;

public interface MainService {
    String getShortLink(String longLink);
    String getLongLink(String shortLink) throws UnknownShortLinkException;
}
