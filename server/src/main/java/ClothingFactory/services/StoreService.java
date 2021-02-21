package ClothingFactory.services;

import ClothingFactory.Constants;
import ClothingFactory.models.ErrorObject;
import ClothingFactory.models.Store;
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
public class StoreService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Store getStoreById(String id) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("get_store_by_id")
                .declareParameters(
                        new SqlParameter("in_id",Types.VARCHAR),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_id",id);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (Store) Utils.jsonToObject(response, Store.class);
        }
        else{
            System.out.println("Error on get by id");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public ArrayList<Store> getAllStores() throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("get_all_stores")
                .declareParameters(
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        MapSqlParameterSource inParamMap = new MapSqlParameterSource();

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (ArrayList<Store>) Utils.jsonToObject(response, ArrayList.class);
        }
        else{
            System.out.println("Error on getAll");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public Store updateStore(String id, Store store) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("update_store")
                .declareParameters(
                        new SqlParameter("in_id",Types.VARCHAR),
                        new SqlParameter("in_store",Types.CLOB),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        String storeJson = Utils.objToJson(store);

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_id",id)
                .addValue("in_store",storeJson);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (Store) Utils.jsonToObject(response, Store.class);
        }
        else{
            System.out.println("Error on update");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }

    public Store createStore(Store store) throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("create_store")
                .declareParameters(
                        new SqlParameter("in_store",Types.CLOB),
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        String storeJson = Utils.objToJson(store);

        MapSqlParameterSource inParamMap = new MapSqlParameterSource("in_store",storeJson);

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (Store) Utils.jsonToObject(response, Store.class);
        }
        else{
            System.out.println("Error on create");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }
}
