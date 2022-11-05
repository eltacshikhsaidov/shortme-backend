package com.fastlogin.falog.repository;

import com.fastlogin.falog.model.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
    Url findByShortUrl(String shortUrl);
    Url findByOriginalUrl(String originalUrl);
}
