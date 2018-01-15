package com.bemobi.shortener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Slf4j
public class ShortenerRespository {

    private static final String INSERT_SHORT_URL = "INSERT INTO SHORT_URL(ALIAS,ORIGINAL_URL,SHORTEN_URL) VALUES (?,?,?)";
    private static final String FIND_SHORT_URL = "SELECT * FROM SHORT_URL WHERE ALIAS = ?";
    private static final String FIND_ORIGINAL_URL = "SELECT * FROM SHORT_URL WHERE SHORTEN_URL= ?";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveUrlShortener(String alias, String originalUrl, String shortUrl) {
        jdbcTemplate.update(INSERT_SHORT_URL, alias, originalUrl, shortUrl);
    }

    public Url findShortUrl(String alias) {
        try {
            return jdbcTemplate.queryForObject(FIND_SHORT_URL, new Object[]{alias}, new ShortUrlRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Url findOriginalUrl(String shortUrl) {
        try {
            return jdbcTemplate.queryForObject(FIND_ORIGINAL_URL, new Object[]{shortUrl}, new ShortUrlRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
