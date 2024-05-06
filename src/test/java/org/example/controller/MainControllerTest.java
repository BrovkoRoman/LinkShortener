package org.example.controller;

import org.example.exception.UnknownShortLinkException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Random;

import static org.example.service.MainServiceImpl.shortLinkLength;
import static org.example.service.MainServiceImpl.shortLinksDomain;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private Random random = new Random();

    @Test
    void testGetShortLink() throws Exception {
        String longLink = "";

        for(int i = 0; i < 10; i++)
            longLink += (char)random.nextInt('a', 'z' + 1);

        MvcResult mvcResult = mockMvc.perform(post("/url/create").content(longLink))
                .andExpect(status().isOk()).andReturn();

        String shortLink = mvcResult.getResponse().getContentAsString();

        mvcResult = mockMvc.perform(post("/url/create").content(longLink))
                .andExpect(status().isOk()).andReturn();

        String shortLink2 = mvcResult.getResponse().getContentAsString();

        assert(shortLink.length() == shortLinksDomain.length() + shortLinkLength);
        assertEquals(shortLink.substring(0, shortLinksDomain.length()), shortLinksDomain);
        assertEquals(shortLink, shortLink2);
    }

    /*@Test
    void testGetShortLink() throws Exception {
        //given:
        String longLink = "www.example.com";
        //when:
        String shortLink = mainService.getShortLink(longLink);
        String shortLink2 = mainService.getShortLink(longLink);
        //then:
        assert(shortLink.length() == shortLinksDomain.length() + shortLinkLength);
        assertEquals(shortLink.substring(0, shortLinksDomain.length()), shortLinksDomain);
        assertEquals(shortLink, shortLink2);
    }*/

    @Test
    void testGetLongLink() throws Exception {
        String longLink = "";

        for(int i = 0; i < 10; i++)
            longLink += (char)random.nextInt('a', 'z' + 1);

        MvcResult mvcResult = mockMvc.perform(post("/url/create").content(longLink))
                .andExpect(status().isOk()).andReturn();

        String shortLink = mvcResult.getResponse().getContentAsString();

        mvcResult = mockMvc.perform(get("/url" + shortLink.substring(shortLinksDomain.length() - 1, shortLink.length())))
                .andExpect(status().isOk()).andReturn();

        String longLink2 = mvcResult.getResponse().getContentAsString();
        assertEquals(longLink, longLink2);
    }

   /* @Test
    void testGetLongLink() throws Exception {
        //given:
        String longLink = "www.example.com";
        //when:
        String shortLink = mainService.getShortLink(longLink);
        String longLink2 = mainService.getLongLink(shortLink);
        //then:
        assertEquals(longLink, longLink2);
    }*/

    @Test
    void testUniqueness() throws Exception {
        int N = 100000;
        HashMap<String, Integer> map = new HashMap<>();

        for(int i = 0; i < N; i++)
        {
            String longLink = "";

            for(int j = 0; j < 3; j++)
                longLink += (char)random.nextInt('a', 'z' + 1);

            longLink += Integer.toString(i);

            MvcResult mvcResult = mockMvc.perform(post("/url/create").content(longLink))
                    .andExpect(status().isOk()).andReturn();

            String shortLink = mvcResult.getResponse().getContentAsString();
            map.put(shortLink, i);
        }

        assertEquals(map.size(), N);
    }

   /* @Test
    void testUniqueness() throws Exception {
        //given:
        int N = 1000000;
        HashMap<String, Integer> map = new HashMap<>();
        //when:
        for(int i = 0; i < N; i++)
        {
            String shortLink = mainService.getShortLink(Integer.toString(i));
            map.put(shortLink, i);
        }
        //then:
        assertEquals(map.size(), N);
    }*/

    @Test
    void testLinkNotFound() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/url/a"))
                .andExpect(status().isOk()).andReturn();

        String longLink = mvcResult.getResponse().getContentAsString();
        assertEquals(longLink, "There is no corresponding long link");
    }

    /*@Test
    void testException() throws Exception {
        //given:
        String shortLink = shortLinksDomain;
        //when:
        boolean exceptionThrown = false;
        try {
            String longLink = mainService.getLongLink(shortLink);
        } catch (UnknownShortLinkException e) {
          exceptionThrown = true;
        }

        assert(exceptionThrown);
    }*/
}
