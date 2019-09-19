package indi.tudan.ddlgenerater;

import indi.tudan.ddlgenerater.service.TableCreater;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DdlGeneraterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdlGeneraterApplication.class, args);

        System.out.println(TableCreater.createTable());
    }

}
