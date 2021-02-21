package ClothingFactory.services;

import ClothingFactory.Constants;
import ClothingFactory.models.Department;
import ClothingFactory.models.ErrorObject;
import ClothingFactory.models.Employee;
import ClothingFactory.utils.Utils;
import ClothingFactory.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Types;
import java.util.Map;
import java.util.ArrayList;


@Service
public class DepartmentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Department getDepartmentById(String id) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("get_department_by_id")
                .declareParameters(
                        new SqlParameter("in_id",Types.VARCHAR),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_id",id);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (Department) Utils.jsonToObject(response, Department.class);
        }
        else{
            System.out.println("Error on get by id");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public ArrayList<Department> getAllDepartments() throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("get_all_departments")
                .declareParameters(
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        MapSqlParameterSource inParamMap = new MapSqlParameterSource();

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (ArrayList<Department>) Utils.jsonToObject(response, ArrayList.class);
        }
        else{
            System.out.println("Error on getAll");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public Department updateDepartment(String id, Department department) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("update_department")
                .declareParameters(
                        new SqlParameter("in_id",Types.VARCHAR),
                        new SqlParameter("in_department",Types.CLOB),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        String departmentJson = Utils.objToJson(department);

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_id",id)
                .addValue("in_department",departmentJson);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (Department) Utils.jsonToObject(response, Department.class);
        }
        else{
            System.out.println("Error on update");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public Department createDepartment(Department department) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("create_Department")
                .declareParameters(
                        new SqlParameter("in_department",Types.CLOB),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        String departmentJson = Utils.objToJson(department);

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_department",departmentJson);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (Department) Utils.jsonToObject(response, Department.class);
        }
        else{
            System.out.println("Error on create");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }
}
