package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;

public class ExceptionMapper
{
    public JsonResponseDTO exception(String message)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage(message);
        responseDTO.setData(null);
        return responseDTO;
    }
}
