package com.greenapp.shopcatalogmanager.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Amount of this item is 0!")
public class ExceededAmountException extends RuntimeException{
}
