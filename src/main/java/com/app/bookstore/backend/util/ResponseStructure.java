package com.app.bookstore.backend.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseStructure<T>
{
    private int status;
    private String message;
    private T data;

    public ResponseStructure<T> setStatus(int status)
    {
        this.status=status;
        return this;
    }


    public ResponseStructure<T> setMessage(String message)
    {
        this.message=message;
        return this;
    }

    public ResponseStructure<T>  setData(T data)
    {
        this.data=data;
        return this;
    }

}
