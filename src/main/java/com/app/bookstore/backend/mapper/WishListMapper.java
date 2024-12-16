package com.app.bookstore.backend.mapper;

import com.app.bookstore.backend.DTO.JsonResponseDTO;
import com.app.bookstore.backend.model.WishList;

import java.util.List;

public class WishListMapper
{
    public JsonResponseDTO returnList(WishList wishList)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("WishList retrieved!!");
        responseDTO.setData(List.of(wishList));
        return responseDTO;
    }

    public JsonResponseDTO inWishList()
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("WishList retrieved!!");
        responseDTO.setData(null);
        return responseDTO;
    }

    public JsonResponseDTO notInWishList()
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(false);
        responseDTO.setMessage("WishList retrieved!!");
        responseDTO.setData(null);
        return responseDTO;
    }

    public JsonResponseDTO wishListRemoved()
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("Book remove from the wishlist");
        responseDTO.setData(null);
        return responseDTO;
    }

    public JsonResponseDTO returnWishList(List<WishList> wishLists)
    {
        JsonResponseDTO responseDTO=new JsonResponseDTO();
        responseDTO.setResult(true);
        responseDTO.setMessage("WishList retrieved");
        responseDTO.setData(wishLists);
        return responseDTO;
    }
}
