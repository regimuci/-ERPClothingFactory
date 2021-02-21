package ClothingFactory.services;

import ClothingFactory.Constants;
import ClothingFactory.models.ErrorObject;
import ClothingFactory.models.Product;
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
public class ProductService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Product getProductById(String id) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("get_product_by_id")
                .declareParameters(
                        new SqlParameter("in_id",Types.VARCHAR),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_id",id);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (Product) Utils.jsonToObject(response, Product.class);
        }
        else{
            System.out.println("Error on get by id");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public ArrayList<Product> getAllProducts() throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("get_all_products")
                .declareParameters(
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        MapSqlParameterSource inParamMap = new MapSqlParameterSource();

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (ArrayList<Product>) Utils.jsonToObject(response, ArrayList.class);
        }
        else{
            System.out.println("Error on getAll");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public Product updateProduct(String id, Product product) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("update_product")
                .declareParameters(
                        new SqlParameter("in_id",Types.VARCHAR),
                        new SqlParameter("in_product",Types.CLOB),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        String productJson = Utils.objToJson(product);

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_id",id)
                .addValue("in_product",productJson);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (Product) Utils.jsonToObject(response, Product.class);
        }
        else{
            System.out.println("Error on update");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public Product createProduct(Product product) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("create_product")
                .declareParameters(
                        new SqlParameter("in_product",Types.CLOB),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        String productJson = Utils.objToJson(product);

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_product",productJson);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (Product) Utils.jsonToObject(response, Product.class);
        }
        else{
            System.out.println("Error on create");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public ArrayList<Product> getProductsByEmployee(String employeeId) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("get_products_by_employee")
                .declareParameters(
                        new SqlParameter("in_employee_id",Types.VARCHAR),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_employee_id",employeeId);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (ArrayList<Product>) Utils.jsonToObject(response, ArrayList.class);
        }
        else{
            System.out.println("Error on get by id");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public ArrayList<Product> getSoldProducts(String storeId) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("get_sold_products")
                .declareParameters(
                        new SqlParameter("in_store_id",Types.VARCHAR),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_store_id",storeId);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (ArrayList<Product>) Utils.jsonToObject(response, ArrayList.class);
        }
        else{
            System.out.println("Error on get get sold products");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }
}
