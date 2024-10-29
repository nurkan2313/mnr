package kg.core.mnr.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    public BaseException(String errorMessage) {
        super(errorMessage);
    }

}