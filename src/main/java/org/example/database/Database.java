package org.example.database;

import java.util.HashMap;
import java.util.Map;

public class Database {
    public final Map<String, String> shortCodes = new HashMap<String, String>(); // key = long link, value = short code (without domain)
    public final Map<String, String> longLinks = new HashMap<String, String>(); // key = short code, value = long link
}
