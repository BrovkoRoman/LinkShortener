package org.example.controller;

import org.example.dao.repository.LinksRepository;
import org.example.exception.BadRepositoryFunctionCallException;
import org.example.exception.IncorrectLongLinkException;
import org.example.exception.UnknownShortLinkException;
import org.example.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.example.service.LinkServiceImpl.shortLinksDomain;

@RestController
@RequestMapping("/url")
public class MainController {
    private final LinkService linkService;
    public MainController(LinkService linkService) {
        this.linkService = linkService;
    }
    @PostMapping(value = "/create")
    public String createShortLink(@RequestBody String longLink)
            throws IncorrectLongLinkException, BadRepositoryFunctionCallException {
        if(longLink == null || longLink.isBlank())
            throw new IncorrectLongLinkException();

        return linkService.getShortLink(longLink);
    }

    @Autowired
    private LinksRepository linksRepository;


    @GetMapping("/{shortCode}")
    public String getLongLink(@PathVariable("shortCode") String shortCode)
            throws UnknownShortLinkException {
        return linkService.getLongLink(shortLinksDomain + shortCode);
    }
}
