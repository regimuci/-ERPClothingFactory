package ClothingFactory.controllers;

import ClothingFactory.Constants;
import ClothingFactory.models.ErrorObject;
import ClothingFactory.models.Product;
import ClothingFactory.services.ProductService;
import ClothingFactory.exceptions.CustomException;
import ClothingFactory.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") String id){
        try{
            return Utils.constructResponse(productService.getProductById(id),HttpStatus.OK);
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

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts(){
        try{
            return Utils.constructResponse(productService.getAllProducts(), HttpStatus.OK);
        }
        catch (CustomException e){
            HttpStatus statusCode = HttpStatus.resolve(e.getErrorObject().getCode());
            return Utils.constructResponse(e.getErrorObject(),statusCode);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            ErrorObject errorObject = new ErrorObject(Constants.ERROR_GENERAL_CODE,Constants.ERROR_GENERAL_MESSAGE);
            return Utils.constructResponse(errorObject,HttpStatus.OK);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product){
        try{
            return Utils.constructResponse(productService.updateProduct(id,product),HttpStatus.OK);
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

    @PostMapping("/product")
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        try{
            return Utils.constructResponse(productService.createProduct(product),HttpStatus.OK);
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

    @GetMapping("/products/{employeeId}/employee")
    public ResponseEntity<Object> getProductsByEmployee(@PathVariable("employeeId") String employeeId){
        try{
            return Utils.constructResponse(productService.getProductsByEmployee(employeeId),HttpStatus.OK);
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

    @GetMapping("/products/{storeId}/sold")
    public ResponseEntity<Object> getSoldProducts(@PathVariable("storeId") String storeId){
        try{
            return Utils.constructResponse(productService.getSoldProducts(storeId),HttpStatus.OK);
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
