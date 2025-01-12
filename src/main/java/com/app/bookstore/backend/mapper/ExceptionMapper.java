package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;

public class ExceptionMapper
{
    public JsonResponseDTO exception(String message)
    {
        return JsonResponseDTO.builder()
                .result(false)
                .data(null)
                .message(message).build();
    }
}
