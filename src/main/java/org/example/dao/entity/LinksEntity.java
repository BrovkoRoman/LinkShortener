package org.example.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "links")
public class LinksEntity {
    @Id
    @Column(name = "SHORT_CODE")
    private String shortCode;
    @Column(name = "LONG_LINK")
    private String longLink;

    public LinksEntity() {}
    public LinksEntity(String shortCode, String longLink) {
        this.shortCode = shortCode;
        this.longLink = longLink;
    }

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
}
