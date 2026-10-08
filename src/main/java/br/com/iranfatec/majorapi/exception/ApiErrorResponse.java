package br.com.iranfatec.majorapi.exception;

public record ApiErrorResponse(

        int statusCode,
        String message


) {}
