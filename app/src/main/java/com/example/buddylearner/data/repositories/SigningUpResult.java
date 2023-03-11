package com.example.buddylearner.data.repositories;

public class SigningUpResult<T> {

    // hide the private constructor to limit subclass types (Success, Error)
    private SigningUpResult() {
    }

    @Override
    public String toString() {
        if (this instanceof SigningUpResult.Success) {
            SigningUpResult.Success success = (SigningUpResult.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof SigningUpResult.Error) {
            SigningUpResult.Error error = (SigningUpResult.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends SigningUpResult {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends SigningUpResult {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }

}
