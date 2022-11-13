package userMgmtSerConsumer;

import jsonRBAC.PojoUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class UserMgmtSerConsume {

//    User user;
//    PojoRole pojoRoleole;
//    Permission permission;

    String reqUrl="http://localhost:8095/users";

    public void callAPI(String reqUrl
//            , Object obj
    ) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        Object respJsonSwag = restTemplate.getForObject(reqUrl, Object.class);

        ObjectMapper mapper =new ObjectMapper();

        String jsonString = respJsonSwag.toString();

        System.out.println(jsonString);

        jsonString= mapper.writeValueAsString(respJsonSwag);

        System.out.println(jsonString);

//        user =new User();
//


//        LinkedHashMap<Object, User>linkedHashMap=new LinkedHashMap<>();
//        HashMap<String,String> hashMap = new HashMap<>();

        LinkedHashMap<String, Object> linkedHashMap= (LinkedHashMap<String, Object>) respJsonSwag;

        PojoUser pojoUserList = new PojoUser();

//        pojoUserList = (PojoUser) respJsonSwag;

//        String name = pojoUserList.getUserList().get(0).getName();

        ArrayList<Object> userList = (ArrayList<Object>) linkedHashMap.get("userList");

        LinkedHashMap<String,Object> user= (LinkedHashMap<String, Object>) userList.get(0);
//
        String name = (String) user.get("name");
//
        System.out.println("###########################   nameFromJson : "+name);


//        Class<PojoUser> PojoUserL = new Class<PojoUser>();
        pojoUserList=mapper.convertValue(respJsonSwag, PojoUser.class);

        System.out.println("###########################   nameFromClass : " + pojoUserList.getUserList().get(1).getName());





    }
}
