package com.example.buddylearner.data.repositories;

public class LoggingInResult<T> {

    // hide the private constructor to limit subclass types (Success, Error)
    private LoggingInResult() {
    }

    @Override
    public String toString() {
        if (this instanceof LoggingInResult.Success) {
            LoggingInResult.Success success = (LoggingInResult.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof LoggingInResult.Error) {
            LoggingInResult.Error error = (LoggingInResult.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends LoggingInResult {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends LoggingInResult {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }

}
