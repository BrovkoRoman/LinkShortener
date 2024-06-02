package org.example.dao.entity;

import jakarta.persistence.*;

@Entity(name = "links")
@Table(indexes = @Index(columnList = "LONG_LINK, SHORT_CODE"))
public class LinksEntity {
    @Id
    @Column(length = 5)
    private String shortCode;
    @Column(length = 1000)
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
