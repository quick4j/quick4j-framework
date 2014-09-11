package com.github.quick4j.core.web.http;

/**
 * @author zhaojh
 */
public class AjaxResponse {
    private int status;
    private Object data;
    private String message;
    private String url;
    private String method;

    public AjaxResponse(Status status) {
        this.status = status.value;
    }

    public AjaxResponse(Status status, String message) {
        this.status = status.value;
        this.message = message;
    }

    public AjaxResponse(Status status, Object data) {
        this.status = status.value;
        this.data = data;
    }

    public AjaxResponse(Status status, String message, String url, String method) {
        this.status = status.value;
        this.message = message;
        this.url = url;
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public int getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public enum Status{
        OK(200, "OK"),
        ERROR(500, "error");

        private int value;
        private String reasonPhrase;
        private Status(int value, String reasonPhrase){
            this.value = value;
            this.reasonPhrase = reasonPhrase;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }
}
