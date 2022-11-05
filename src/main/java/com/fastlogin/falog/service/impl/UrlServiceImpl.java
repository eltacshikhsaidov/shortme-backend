package com.fastlogin.falog.service.impl;

import com.fastlogin.falog.exception.ExceptionConstants;
import com.fastlogin.falog.model.Url;
import com.fastlogin.falog.repository.UrlRepository;
import com.fastlogin.falog.request.Request;
import com.fastlogin.falog.response.RespStatus;
import com.fastlogin.falog.response.Response;
import com.fastlogin.falog.service.UrlService;
import com.fastlogin.falog.util.UrlUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class UrlServiceImpl implements UrlService {

    private static final String HOST_ADDRESS = "http://localhost:8080/";

    private final UrlUtil urlUtil;

    private final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlUtil urlUtil, UrlRepository urlRepository) {
        this.urlUtil = urlUtil;
        this.urlRepository = urlRepository;
    }


    @Override
    public Response shortUrl(Request request) {
        log.info(
                String.format("function calling with parameters, request: %s", request)
        );
        Response response = new Response();

        String originalUrl = request.getOriginalUrl();

        if (originalUrl == null) {
            log.info("url can not be null");
            response.setStatus(
                    new RespStatus(
                            ExceptionConstants.URL_CAN_NOT_BE_NULL,
                            "url can not be null"
                    )
            );
            return response;
        } else if (!urlUtil.isValidUrl(originalUrl)) {
            response.setStatus(
                    new RespStatus(
                            ExceptionConstants.URL_NOT_VALID,
                            "url is not valid format"
                    )
            );
            return response;
        }

        log.info("Checking if original url is present in our database");
        Url checkInDb = urlRepository.findByOriginalUrl(originalUrl);
        log.info(
                String.format("Is short url present in database -> %s", !(checkInDb==null))
        );

        if (checkInDb == null) {
            Url url = new Url();
            String shortUrl = urlUtil.generateUniqueString(5);
            url.setShortUrl(shortUrl);
            url.setOriginalUrl(request.getOriginalUrl());
            urlRepository.save(url);
            response.setShortUrl(HOST_ADDRESS + shortUrl);
        } else {
            response.setShortUrl(HOST_ADDRESS + checkInDb.getShortUrl());
        }
        response.setStatus(RespStatus.getSuccessMessage());

        log.info("Function response: success");
        return response;
    }

    @Override
    public void redirectToOriginalUrl(String shortUrl, HttpServletResponse response) {
        log.info("Redirecting short url" + shortUrl + " to original url");
        try {
            String originalUrl = urlRepository.findByShortUrl(shortUrl).getOriginalUrl();
            log.info("redirecting status: success");
            response.sendRedirect(originalUrl);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(ExceptionConstants.URL_NOT_FOUND, "Url not found", e);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Could not redirect to the original url", e
            );
        }
    }
}
