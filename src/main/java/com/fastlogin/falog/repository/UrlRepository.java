package com.fastlogin.falog.repository;

import com.fastlogin.falog.model.Url;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
    Url findByShortUrl(String shortUrl);
    Url findByOriginalUrl(String originalUrl);

    @Modifying
    @Query("UPDATE Url u SET u.clickCount = :count WHERE u.id = :urlId")
    void updateCountByUrlId(@Param("count") Integer count, @Param("urlId") Long urlId);
}
