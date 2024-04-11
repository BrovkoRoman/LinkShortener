package org.example.service;

import org.example.exception.UnknownShortLinkException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.example.service.MainServiceImpl.shortLinkLength;
import static org.example.service.MainServiceImpl.shortLinksDomain;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainServiceTest {
    private final MainService service = new MainServiceImpl();

    @Test
    void testGetShortLink() throws Exception {
        //given:
        String longLink = "www.example.com";
        //when:
        String shortLink = service.getShortLink(longLink);
        String shortLink2 = service.getShortLink(longLink);
        //then:
        assert(shortLink.length() == shortLinksDomain.length() + shortLinkLength);
        assertEquals(shortLink.substring(0, shortLinksDomain.length()), shortLinksDomain);
        assertEquals(shortLink, shortLink2);
    }

    @Test
    void testGetLongLink() throws Exception {
        //given:
        String longLink = "www.example.com";
        //when:
        String shortLink = service.getShortLink(longLink);
        String longLink2 = service.getLongLink(shortLink);
        //then:
        assertEquals(longLink, longLink2);
    }

    @Test
    void testUniqueness() throws Exception {
        //given:
        int N = 1000000;
        HashMap<String, Integer> map = new HashMap<>();
        //when:
        for(int i = 0; i < N; i++)
        {
            String shortLink = service.getShortLink(Integer.toString(i));
            map.put(shortLink, i);
        }
        //then:
        assertEquals(map.size(), N);
    }

    @Test
    void testException() throws Exception {
        //given:
        String shortLink = shortLinksDomain;
        //when:
        boolean exceptionThrown = false;
        try {
            String longLink = service.getLongLink(shortLink);
        } catch (UnknownShortLinkException e) {
          exceptionThrown = true;
        }

        assert(exceptionThrown);
    }
}
