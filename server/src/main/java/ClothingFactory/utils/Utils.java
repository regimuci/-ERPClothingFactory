package ClothingFactory.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public class Utils {

    public static Object jsonToObject(String jsonString, Class targetClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, targetClass);
    }

    public static String objToJson(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    public static ResponseEntity<Object> constructResponse(Object service, HttpStatus status){
        // CORS Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin","*");
        headers.add("Access-Control-Allow-Headers","Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
        headers.add("Access-Control-Allow-Methods","DELETE,GET,HEAD,OPTIONS,PATCH,POST,PUT");

        return new ResponseEntity<>(service,headers,status);
    }

}
