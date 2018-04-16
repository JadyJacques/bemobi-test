package com.bemobi.shortener;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResponseCode {
    ALIAS_EXISTS("001", "CUSTOM ALIAS ALREADY EXISTS"),
    URL_NOT_FOUND("002", "SHORTENED URL NOT FOUND");

    private String errCode;
    private String description;

    ResponseCode(String errCode, String description) {
        this.errCode = errCode;
        this.description = description;
    }
}
