package org.example.database;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static final Database instance = new Database();
    private Database() {}
    public static Database getInstance() {
        return instance;
    }

    private final Map<String, String> shortCodes = new HashMap<>(); // key = long link, value = short code (without domain)
    private final Map<String, String> longLinks = new HashMap<>(); // key = short code, value = long link

    public String getShortCode(String longLink) {
        return shortCodes.get(longLink);
    }

    public String getLongLink(String shortCode) {
        return longLinks.get(shortCode);
    }

    public boolean containsLongLink(String longLink) {
        return shortCodes.containsKey(longLink);
    }

    public boolean containsShortCode(String shortCode) {
        return longLinks.containsKey(shortCode);
    }

    public void addPairOfLinks(String longLink, String shortCode) {
        longLinks.put(shortCode, longLink);
        shortCodes.put(longLink, shortCode);
    }
}
