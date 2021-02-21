package ClothingFactory.exceptions;

import ClothingFactory.models.ErrorObject;

public class CustomException extends Exception {
    private ErrorObject errorObject;

    public CustomException(ErrorObject errorObject){
        super(errorObject.getMessage());
        this.errorObject = errorObject;
    }

    public ErrorObject getErrorObject() {
        return errorObject;
    }

    public void setErrorObject(ErrorObject errorObject) {
        this.errorObject = errorObject;
    }
}
