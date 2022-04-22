package com.task.locatorptm.data.utils;

public abstract class Resource<T> {
    T data;
    String message;

    public Resource(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public final static class Success<T> extends Resource<T> {
        public T data;

        public T getData() {
            return data;
        }

        public Success(T data) {
            super(data, null);
            this.data = data;
        }
    }

    public final static class Error<T> extends Resource<T> {

        public String message;
        public T data;

        public String getMessage() {
            return message;
        }

        public T getData() {
            return data;
        }

        public Error(T data, String message) {
            super(data, message);
            this.message = message;
            this.data = data;
        }
    }
}
