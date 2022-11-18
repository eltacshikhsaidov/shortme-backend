package com.fastlogin.falog.schedule;

import com.fastlogin.falog.model.Url;
import com.fastlogin.falog.repository.UrlRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableAsync
@Log4j2
public class UrlScheduler {

    private final UrlRepository urlRepository;

    public UrlScheduler(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Async
    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    public void removeUrl() {
        log.info("removeUrl schedule started");
        List<Url> urlList = (List<Url>) urlRepository.findAll();
        urlList.forEach(
                url -> {
                    if (url.getDate().getTime() + 4 * 60 * 1000 < new Date().getTime()) {
                        log.info(
                                String.format("Removing url with short url -> %s", url.getShortUrl())
                        );
                        urlRepository.delete(url);
                        log.info("Url successfully removed from database");
                    } else {
                        log.info(
                                String.format("Url is not expired yet, short url -> %s",url.getShortUrl())
                        );
                    }
                }
        );
        log.info("removeUrl schedule ended");
    }
}
