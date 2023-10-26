package com.katana.spring.resolver;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

/**
 * 错误消息响应对象
 */
@Builder
@Data
public class ErrorResponse {

    private final String errorCode;
    private final String message;
    @Singular
    private final List<ErrorField> fields;


    /**
     * 错误字段
     */
    public record ErrorField(String field, String message) {
    }

}
