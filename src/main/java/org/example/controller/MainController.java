package org.example.controller;

import org.example.exception.UnknownShortLinkException;
import org.example.service.MainService;
import org.example.service.MainServiceImpl;
import org.springframework.web.bind.annotation.*;

import static org.example.service.MainServiceImpl.shortLinksDomain;

@RestController
public class MainController {
    private final MainService mainService;
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }
    @PostMapping(value = "/create")
    public String createShortLink(@RequestBody String longLink) {


            if(longLink == null || longLink.isBlank())
                return "Incorrect link";

            return mainService.getShortLink(longLink);

    }


    @GetMapping("/{shortCode}")
    public String getLongLink(@PathVariable("shortCode") String shortCode) {


            try {
                return mainService.getLongLink(shortLinksDomain + shortCode);
            } catch(UnknownShortLinkException e) {
                return "There is no corresponding long link";
            }


    }
}
