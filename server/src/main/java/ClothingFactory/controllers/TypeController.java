package ClothingFactory.controllers;

import ClothingFactory.Constants;
import ClothingFactory.models.ErrorObject;
import ClothingFactory.exceptions.CustomException;
import ClothingFactory.services.TypeService;
import ClothingFactory.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public ResponseEntity<Object> getTypes(){
        try{
            return Utils.constructResponse(typeService.getTypes(), HttpStatus.OK);
        }
        catch (CustomException e){
            HttpStatus statusCode = HttpStatus.resolve(e.getErrorObject().getCode());
            return Utils.constructResponse(e.getErrorObject(),statusCode);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            ErrorObject errorObject = new ErrorObject(Constants.ERROR_GENERAL_CODE,Constants.ERROR_GENERAL_MESSAGE);
            return Utils.constructResponse(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
