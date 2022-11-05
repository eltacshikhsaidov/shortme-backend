package com.fastlogin.falog.service;

import com.fastlogin.falog.request.Request;
import com.fastlogin.falog.response.Response;

import javax.servlet.http.HttpServletResponse;

public interface UrlService {
    Response shortUrl(Request request);

    void redirectToOriginalUrl(String shortUrl, HttpServletResponse response);
}
