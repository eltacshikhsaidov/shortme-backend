package com.fastlogin.falog.schedule;

import com.fastlogin.falog.model.Url;
import com.fastlogin.falog.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableAsync
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UrlScheduler {

    private final UrlRepository urlRepository;

    @Async
    @Scheduled(cron = "0 0 23 L * ?")
    public void removeUrl() {
        log.info("removeUrl schedule started");
        List<Url> urlList = (List<Url>) urlRepository.findAll();

        if (urlList.isEmpty()) {
            log.warn("there is not any url yet");
            return;
        }

        urlList.forEach(
                url -> {
                    if (url.getDate().getTime() < new Date().getTime()) {
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
