package com.fastlogin.falog.webservice;

import com.fastlogin.falog.request.Request;
import com.fastlogin.falog.response.Response;
import com.fastlogin.falog.response.Result;
import com.fastlogin.falog.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UrlController {
    private final UrlService urlService;

    @PostMapping(value = "api/shortUrl")
    public Result<?> shortUrl(@RequestBody Request request) {
        return urlService.shortUrl(request);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToOriginalUrl(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable String shortUrl
    ) {
        urlService.redirectToOriginalUrl(shortUrl, response, request);
    }
}
