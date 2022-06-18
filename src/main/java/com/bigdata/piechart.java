package com.bigdata;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class piechart {
    ChartPanel frame1; //框架
    //构造饼状图
    public piechart()
    {
        DefaultPieDataset dataset=getDataset();
        JFreeChart chart= ChartFactory.createPieChart(
                "商品所占购买份额",
                dataset,
                true,
                false,
                false
        );

        PiePlot plot=(PiePlot) chart.getPlot();  //布局
        StandardPieSectionLabelGenerator sp=new StandardPieSectionLabelGenerator("{0} ({2})"); //显示名称和百分比，{1}数值
        plot.setLabelGenerator(sp); //显示百分比
        plot.setBackgroundPaint(Color.WHITE);

        chart.getTitle().setFont(new Font("黑体",Font.BOLD,20)); //标题
        PiePlot plot1=(PiePlot) chart.getPlot();  //jieshi的内容
        plot1.setLabelFont(new Font("宋体",Font.BOLD,10)); //解释框文字
        plot1.setLabelBackgroundPaint(Color.YELLOW);
        chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,10));  //名称，子标题

        frame1=new ChartPanel(chart,true);
    }

    //保存数据库数据
    public List<fp_result> queryData()
    {
        database da=new database();
        da.initmysql();
        String sql4="select `产品名称`,value from fp_result";
        List<fp_result> list=new ArrayList<>();
        try {
            da.rs=da.statement.executeQuery(sql4);

            while(da.rs.next()){
                list.add(new fp_result(da.rs.getString(1),da.rs.getInt(2)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    //获取数据
    private DefaultPieDataset getDataset()
    {
        DefaultPieDataset dataset=new DefaultPieDataset();

        List<fp_result> list=queryData();

        for (fp_result fp_result:list)
            dataset.setValue(fp_result.getName(),fp_result.getValue());
        return dataset;
    }

    //显示
    public void run()
    {
        JFrame frame=new JFrame("饼状图");
        frame.setBounds(150,10,1000,1000);
        frame.add(frame1);
        frame.setVisible(true);
    }
}
