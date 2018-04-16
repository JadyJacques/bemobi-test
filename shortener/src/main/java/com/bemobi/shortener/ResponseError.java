package com.bemobi.shortener;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseError {
    private String alias;
    private String errCode;
    private String description;

    public ResponseError(String errCode, String description) {
        this.errCode = errCode;
        this.description = description;
    }
}
