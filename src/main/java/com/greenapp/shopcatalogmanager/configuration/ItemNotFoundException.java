package com.greenapp.shopcatalogmanager.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested reward is not found")
public class ItemNotFoundException extends RuntimeException {

}
