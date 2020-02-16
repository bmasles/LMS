package com.smoothstack.lms.borrowermicroservice;

import com.smoothstack.lms.borrowermicroservice.database.sql.SQLDataMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BorrowerMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BorrowerMicroserviceApplication.class, args);

        SQLDataMap map = new SQLDataMap();
        map.put("TableName", "AAAAAA");
        map.put("id", 2);

        map.forEach((k,v)->
            System.out.printf("%s = %s\n", k, v));
        System.out.println(map.keyValueReferenceCsv());
    }

}
