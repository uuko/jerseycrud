package com.jersey.test.exception;

import com.jersey.test.base.BaseException;

import javax.ws.rs.core.Response;

public class ClientException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ClientException(String message) {
        this(null, message);
    }
    public ClientException(String code, String message) {
        this(code, message, null);
    }

    public ClientException(String code, String message, String field) {
        this(code, null, field, message);
    }

    public ClientException(String code, String reason, String field, String message) {
        super(Response.Status.BAD_REQUEST, code, reason, field, message);
    }

    public ClientException(Response.Status state,String code, String message) {
        super(state, code, null,null, message);
    }

    public ClientException(Response.Status state) {
        super(state, null, null, null, state.getReasonPhrase());
    }

    public final Response getResponseWithoutContent(){
        return Response.status(this.getStatus()).entity("").build();
    }
}
