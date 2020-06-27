package com.greenapp.shopcatalogmanager.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Billing process interrupted and no fallback available")
public class BillingFailedException extends RuntimeException {
}
