package com.bemobi.shortener;

import lombok.Data;

@Data
public class Url {
    private String alias;
    private String shortenUrl;
    private String url;
}
