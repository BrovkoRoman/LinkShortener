package org.example.controller;

import org.example.exception.BadRepositoryFunctionCallException;
import org.example.exception.IncorrectLongLinkException;
import org.example.exception.UnknownShortLinkException;
import org.example.service.MainService;
import org.example.service.MainServiceImpl;
import org.springframework.web.bind.annotation.*;

import static org.example.service.MainServiceImpl.shortLinksDomain;

@RestController
@RequestMapping("/url")
public class MainController {
    private final MainService mainService;
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }
    @PostMapping(value = "/create")
    public String createShortLink(@RequestBody String longLink)
            throws IncorrectLongLinkException, BadRepositoryFunctionCallException {
        if(longLink == null || longLink.isBlank())
            throw new IncorrectLongLinkException();

        return mainService.getShortLink(longLink);
    }


    @GetMapping("/{shortCode}")
    public String getLongLink(@PathVariable("shortCode") String shortCode)
            throws UnknownShortLinkException, BadRepositoryFunctionCallException {
        return mainService.getLongLink(shortLinksDomain + shortCode);
    }
}
