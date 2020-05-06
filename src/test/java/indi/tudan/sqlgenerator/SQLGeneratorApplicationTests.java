/*
package indi.tudan.sqlgenerator;

import cn.hutool.core.util.NumberUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SQLGeneratorApplicationTests {

    @Test
    public void contextLoads() {

        // 查询上月数据
        System.out.println(DateUtils.addMonths("2019-03", -1));
        System.out.println(DateUtils.addMonthsWithDay("2019-03-31", -1));
        System.out.println(DateUtils.addMonthsWithDay("2019-02-28", -1));

        BigDecimal rate = new BigDecimal(1).divide(new BigDecimal(3), 6, BigDecimal.ROUND_HALF_UP);
        System.out.println(rate);
        System.out.println(NumberUtil.decimalFormat("#.##%", NumberUtil.div(new BigDecimal(1), new BigDecimal(3)).doubleValue()));

        System.out.println(NumberUtils.getRingRatioByMonth(5, 3));
    }

}
*/
