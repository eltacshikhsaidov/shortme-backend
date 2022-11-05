package com.fastlogin.falog.webservice;

import com.fastlogin.falog.request.Request;
import com.fastlogin.falog.response.Response;
import com.fastlogin.falog.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;

@RestController
@CrossOrigin(origins = "*")
public class UrlController {
    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "api/shortUrl")
    public Response shortUrl(@RequestBody Request request) {
        return urlService.shortUrl(request);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToOriginalUrl(HttpServletResponse response, @PathVariable String shortUrl) {
        urlService.redirectToOriginalUrl(shortUrl, response);
    }
}
