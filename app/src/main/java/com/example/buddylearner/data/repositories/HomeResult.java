package com.example.buddylearner.data.repositories;

public class HomeResult<T> {

    // hide the private constructor to limit subclass types (Success, Error)
    private HomeResult() {
    }

    @Override
    public String toString() {
        if (this instanceof HomeResult.Success) {
            HomeResult.Success success = (HomeResult.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof HomeResult.Error) {
            HomeResult.Error error = (HomeResult.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends HomeResult {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends HomeResult {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }

}
