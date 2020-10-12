package br.com.joao.ourdigitalbank.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private T data;
    private Object errors;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public void addErrorMsgToResponse(String msgError) {
        ResponseError error = new ResponseError();
        error.setDetails(msgError);
        error.setTimestamp(LocalDateTime.now());

        setErrors(error);
    }
}
