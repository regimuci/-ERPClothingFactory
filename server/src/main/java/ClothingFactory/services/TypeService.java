package ClothingFactory.services;

import ClothingFactory.Constants;
import ClothingFactory.models.ErrorObject;
import ClothingFactory.models.Type;
import ClothingFactory.utils.Utils;
import ClothingFactory.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Types;
import java.util.Map;
import java.util.ArrayList;


@Service
public class TypeService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ArrayList<Type> getTypes() throws CustomException, IOException {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName(Constants.DB_PACKAGE)
                .withFunctionName("get_types")
                .declareParameters(
                        new SqlOutParameter("response",Types.LONGVARCHAR)
                );

        MapSqlParameterSource inParamMap = new MapSqlParameterSource();

        Map<String, Object> execute = call.execute(inParamMap);

        int result = ((java.math.BigDecimal) execute.get("return")).intValue();
        String response = (String) execute.get("response");


        if(result == Constants.RESULT_OK){
            return (ArrayList<Type>) Utils.jsonToObject(response, ArrayList.class);
        }
        else{
            System.out.println("Error on getAll");
            ErrorObject errorObject = (ErrorObject) Utils.jsonToObject(response, ErrorObject.class);
            throw new CustomException(errorObject);
        }
    }
}
