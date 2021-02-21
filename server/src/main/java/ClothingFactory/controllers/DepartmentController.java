package ClothingFactory.controllers;

import ClothingFactory.Constants;
import ClothingFactory.models.Department;
import ClothingFactory.models.ErrorObject;
import ClothingFactory.exceptions.CustomException;
import ClothingFactory.services.DepartmentService;
import ClothingFactory.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/department/{id}")
    public ResponseEntity<Object> getDepartmentById(@PathVariable("id") String id){
        try{
            return Utils.constructResponse(departmentService.getDepartmentById(id), HttpStatus.OK);
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

    @GetMapping("/departments")
    public ResponseEntity<Object> getAllDepartments(){
        try{
            return Utils.constructResponse(departmentService.getAllDepartments(), HttpStatus.OK);
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

    @PutMapping("/department/{id}")
    public ResponseEntity<Object> updateDepartment(@PathVariable("id") String id, @RequestBody Department department){
        try{
            return Utils.constructResponse(departmentService.updateDepartment(id,department), HttpStatus.OK);
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

    @PostMapping("/department")
    public ResponseEntity<Object> createDepartment(@RequestBody Department department){
        try{
            return Utils.constructResponse(departmentService.createDepartment(department), HttpStatus.OK);
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
