package indi.tudan.ddlgenerater;

import indi.tudan.ddlgenerater.service.TableCreater;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DdlGeneraterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdlGeneraterApplication.class, args);

        TableCreater.builder().ftlName("create").build().parseTablesDDL("C:\\Users\\wtuda\\Documents\\WorkLearn\\ddl-generater\\src\\main\\resources\\static\\集团大屏新专题与预警模型0919.xlsx");
    }

}
