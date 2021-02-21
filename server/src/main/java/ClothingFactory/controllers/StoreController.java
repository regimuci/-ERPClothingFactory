package ClothingFactory.controllers;

import ClothingFactory.Constants;
import ClothingFactory.models.ErrorObject;
import ClothingFactory.exceptions.CustomException;
import ClothingFactory.models.Store;
import ClothingFactory.services.StoreService;
import ClothingFactory.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/store/{id}")
    public ResponseEntity<Object> getStoreById(@PathVariable("id") String id){
        try{
            return Utils.constructResponse(storeService.getStoreById(id), HttpStatus.OK);
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

    @GetMapping("/stores")
    public ResponseEntity<Object> getAllStores(){
        try{
            return Utils.constructResponse(storeService.getAllStores(), HttpStatus.OK);
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

    @PutMapping("/store/{id}")
    public ResponseEntity<Object> updateStore(@PathVariable("id") String id, @RequestBody Store store){
        try{
            return Utils.constructResponse(storeService.updateStore(id,store), HttpStatus.OK);
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

    @PostMapping("/store")
    public ResponseEntity<Object> createStore(@RequestBody Store store){
        try{
            return Utils.constructResponse(storeService.createStore(store), HttpStatus.OK);
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
