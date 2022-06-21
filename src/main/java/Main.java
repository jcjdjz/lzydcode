import com.bigdata.*;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.fpm.AssociationRules;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        database da = new database();
        //da.begin();
        draw draw=new draw();
        //draw.test();

        //spark引擎的关联关系挖掘的FP-growth算法
        SparkConf conf = new SparkConf().setAppName("FP_growth").setMaster("local"); //引擎配置
        JavaSparkContext sc = new JavaSparkContext(conf); //java的spark环境

        //加载数据，分布式存储
        JavaRDD<String> data = sc.textFile("F:/exercise_bef.txt");
        JavaRDD<List<String>> transactions = data.map(new Function<String, List<String>>() {  //处理数据
            public List<String> call(String sentence) { //数据
                String[] parts = sentence.split(","); //分割数据
                return Arrays.asList(parts);
            }
        });

        //创建算法实例，设置最小支持度和数据分区
        FPGrowth fpGrowth = new FPGrowth().setMinSupport(0.01).setNumPartitions(3);
        FPGrowthModel<String> model = fpGrowth.run(transactions); //开始训练

        //频繁基和次数
        for(FPGrowth.FreqItemset<String> itemset : model.freqItemsets().toJavaRDD().collect()) {
            System.out.println(itemset.javaItems() + "," + itemset.freq());
            String javaitem=itemset.javaItems().toString();
            String freq="'"+itemset.freq()+"'";
            //da.paste(javaitem,freq);
            //dt.addRow();
        }

        for(AssociationRules.Rule<String> rule:model.generateAssociationRules(0.5).toJavaRDD().collect()) {
            System.out.println(rule.javaAntecedent() + "=>" + rule.javaConsequent() + "," + rule.confidence());
            String javaitem=rule.javaAntecedent() + "=>" + rule.javaConsequent();
            String freq="'"+rule.confidence()+"'";
            //da.paste1(javaitem,freq);
        }
        sc.close();

        rs_table rs=new rs_table();
        //rs.show();
        //rs.show1();
        draw.run();
        piechart piechart=new piechart();
        piechart.run();

    }
}

