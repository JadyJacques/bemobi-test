package com.bemobi.shortener;

import lombok.Data;

@Data
public class ShortUrlResponse {
    private String alias;
    private String url;
    private Statistic statistic;
}
