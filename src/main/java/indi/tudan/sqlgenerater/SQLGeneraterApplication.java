package indi.tudan.sqlgenerater;

import indi.tudan.sqlgenerater.service.SQLGenerater;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SQLGeneraterApplication {

    public static void main(String... args) {

        SpringApplication.run(SQLGeneraterApplication.class, args);

        // --t=create --m=console --p="C:/Users/tudan/Desktop/差异化结算大屏/集团大屏新专题与预警模型0919.xlsx" --r="C:/Users/tudan/Desktop/差异化结算大屏/ddl"
        // 程序开始执行
        SQLGenerater.exec(args);
    }

}
