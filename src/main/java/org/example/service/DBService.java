package org.example.service;

public interface DBService {
    String getShortCodeFromDB(String longLink);
    String getLongLinkFromDB(String shortCode);
    boolean containsShortCode(String shortCode);
    boolean containsLongLink(String longLink);
    void addPairOfLinks(String longLink, String shortCode);
    void deletePairOfLinks(String longLink, String shortCode);
}
