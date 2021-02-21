package ClothingFactory.controllers;

import ClothingFactory.Constants;
import ClothingFactory.models.ErrorObject;
import ClothingFactory.models.Employee;
import ClothingFactory.exceptions.CustomException;
import ClothingFactory.services.EmployeeService;
import ClothingFactory.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable("id") String id){
        try{
            return Utils.constructResponse(employeeService.getEmployeeById(id),HttpStatus.OK);
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

    @GetMapping("/employees")
    public ResponseEntity<Object> getAllEmployees(){
        try{
            return Utils.constructResponse(employeeService.getAllEmployees(),HttpStatus.OK);
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

    @PutMapping("/employee/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employee){
        try{
            return Utils.constructResponse(employeeService.updateEmployee(id,employee),HttpStatus.OK);
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

    @PostMapping("/employee")
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee){
        try{
            return Utils.constructResponse(employeeService.createEmployee(employee),HttpStatus.OK);
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
