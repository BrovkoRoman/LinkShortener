package org.example.service;

import org.example.exception.BadRepositoryFunctionCallException;
import org.example.exception.UnknownShortLinkException;

public interface MainService {
    String getShortLink(String longLink) throws BadRepositoryFunctionCallException;
    String getLongLink(String shortLink) throws BadRepositoryFunctionCallException, UnknownShortLinkException;
}
