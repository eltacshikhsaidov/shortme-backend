package com.fastlogin.falog.webservice;

import com.fastlogin.falog.request.Request;
import com.fastlogin.falog.response.Result;
import com.fastlogin.falog.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = {"http://localhost:8080/", "http://134.122.123.18:8080/", "*"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UrlController {
    private final UrlService urlService;

    @PostMapping(value = "api/shortUrl")
    public Result<?> shortUrl(@RequestBody Request request) {
        return urlService.shortUrl(request);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToOriginalUrl(HttpServletResponse response, @PathVariable String shortUrl) {
        urlService.redirectToOriginalUrl(shortUrl, response);
    }
}
