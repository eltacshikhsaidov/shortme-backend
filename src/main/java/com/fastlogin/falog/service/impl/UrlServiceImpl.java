package com.fastlogin.falog.service.impl;

import com.fastlogin.falog.model.Url;
import com.fastlogin.falog.repository.UrlRepository;
import com.fastlogin.falog.request.Request;
import com.fastlogin.falog.response.Result;
import com.fastlogin.falog.service.UrlService;
import com.fastlogin.falog.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.fastlogin.falog.exception.ExceptionConstants.*;
import static com.fastlogin.falog.response.Result.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UrlServiceImpl implements UrlService {

    private static final String HOST_ADDRESS = "http://localhost:8080/";
//    private static final String HOST_ADDRESS = "http://134.122.123.18:8080/";

    private final UrlUtil urlUtil;

    private final UrlRepository urlRepository;

    @Override
    public Result<?> shortUrl(Request request) {
        log.info("function calling with parameters, request: {}", request.originalUrl());
        String originalUrl = request.originalUrl();

        if (originalUrl == null) {
            log.info("url can not be null");
            return failed(URL_CAN_NOT_BE_NULL, "url can not be null");
        } else if (!urlUtil.isValidUrl(originalUrl)) {
            log.info("url is not valid");
            return failed(URL_NOT_VALID, "url is not valid format");
        }

        log.info("checking if original url is present in our database");
        Url checkInDb = urlRepository.findByOriginalUrl(originalUrl);
        log.info("is short url present in database: {}", nonNull(checkInDb));

        String shortUrl;

        if (isNull(checkInDb)) {
            Url url = new Url();
            shortUrl = urlUtil.generateUniqueString(5);
            url.setShortUrl(shortUrl);
            url.setOriginalUrl(request.originalUrl());
            log.info("creating new short url for {}", originalUrl);
            urlRepository.save(url);
        } else {
            log.info("using existing url in db for {}", originalUrl);
            shortUrl = checkInDb.getShortUrl();
        }

        String fullUrl = HOST_ADDRESS.concat(shortUrl);

        log.info("function response: success");
        return success(fullUrl);
    }

    @SneakyThrows
    @Override
    @Transactional
    public void redirectToOriginalUrl(String shortUrl, HttpServletResponse response, HttpServletRequest request) {
        log.info("redirecting short url" + shortUrl + " to original url");
        Url url = urlRepository.findByShortUrl(shortUrl);
        System.out.println(url.getOriginalUrl());
        String originalUrl = url.getOriginalUrl();
        updateClick(url.getId());
        log.info("redirecting status: success");
        response.sendRedirect(originalUrl);
    }

    public void updateClick(Long id) {
        Url url = urlRepository.findById(id).orElseThrow();
        Integer increment = url.getClickCount() + 1;
        url.setClickCount(increment);
        urlRepository.updateCountByUrlId(increment, id);
    }
}
