package com.bemobi.shortener;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShortUrlRowMapper implements RowMapper<Url> {

    @Override
    public Url mapRow(ResultSet resultSet, int i) throws SQLException {
        Url url = new Url();
        url.setAlias(resultSet.getString("alias"));
        url.setShortenUrl(resultSet.getString("shorten_url"));
        url.setUrl(resultSet.getString("original_url"));
        return url;
    }
}
