package org.example.service;

import org.example.exception.BadRepositoryFunctionCallException;
import org.example.exception.UnknownShortLinkException;

import java.sql.SQLException;

public interface MainService {
    String getShortLink(String longLink) throws SQLException, BadRepositoryFunctionCallException;
    String getLongLink(String shortLink) throws SQLException, BadRepositoryFunctionCallException, UnknownShortLinkException;
}
