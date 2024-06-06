package org.example.dao;

import lombok.Data;
import org.example.dao.entity.LinksEntity;

@Data
public class LinksDao {
    private String shortCode;
    private String longLink;

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getLongLink() {
        return longLink;
    }

    public void setLongLink(String longLink) {
        this.longLink = longLink;
    }

    public LinksDao(String shortCode, String longLink) {
        this.shortCode = shortCode;
        this.longLink = longLink;
    }

    public LinksDao(LinksEntity entity) {
        this.longLink = entity.getLongLink();
        this.shortCode = entity.getShortCode();
    }
}
