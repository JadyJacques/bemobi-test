package com.bemobi.shortener;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.bemobi.shortener.ResponseCode.ALIAS_EXISTS;
import static com.bemobi.shortener.ResponseCode.URL_NOT_FOUND;

@RestController
@Slf4j
public class MainController {

    @Autowired
    URLShortener urlShortener;

    @Autowired
    Gson gson;

    @Autowired
    ShortenerRespository repository;

    @PutMapping(value = "/create")
    @CrossOrigin
    public String shortenUrl(@RequestParam(value = "url") String originalUrl,
                             @RequestParam(value = "custom_alias", required = false) String customAlias) {
        log.info("Recebendo pedido de encurtamento da url {} com o alias {}", originalUrl, customAlias);
        long start = System.currentTimeMillis();
        final String shortUrl = urlShortener.shortenURL(originalUrl);
        if (customAlias == null) {
            customAlias = generateAlias();
        }
        if (aliasExists(customAlias)) {
            ResponseError responseError = new ResponseError(customAlias, ALIAS_EXISTS.getErrCode(),
                    ALIAS_EXISTS.getDescription());
            log.error("Erro {}", responseError);
            return gson.toJson(responseError);
        }
        repository.saveUrlShortener(customAlias, originalUrl, shortUrl);
        ShortUrlResponse response = new ShortUrlResponse();
        response.setAlias(customAlias);
        response.setUrl(shortUrl);
        long elapsed = System.currentTimeMillis() - start;
        response.setStatistic(new Statistic(elapsed));
        final String responseJson = gson.toJson(response);
        log.info("Retornando o response {}", responseJson);
        return responseJson;
    }

    private boolean aliasExists(String alias) {
        return repository.findShortUrl(alias) != null;
    }

    private String generateAlias() {
        Random random = ThreadLocalRandom.current();
        byte[] randomBytes = new byte[6];
        random.nextBytes(randomBytes);
        return Base64.encodeBase64String(randomBytes);
    }

    @GetMapping("/retrieve")
    @CrossOrigin
    public String retrieveUrl(@RequestParam(name = "url") String shortUrl) {
        final Url urlOriginal = repository.findOriginalUrl(shortUrl);
        if (urlOriginal == null) {
            return gson.toJson(new ResponseError(URL_NOT_FOUND.getErrCode(), URL_NOT_FOUND.getDescription()));
        }
        return gson.toJson(urlOriginal);
    }
}