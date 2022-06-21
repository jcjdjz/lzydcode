package com.bigdata;

import javafx.scene.chart.BarChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;


public class draw {

    ChartPanel frame; //框架

    /*public static void drawing() {
        database da = new database();
        da.initmysql();
        //查看fp_result
        String sql4 = "select `产品名称`,value from fp_result";

        List data1=new ArrayList();
        //读取数据库里的值
        try {
            da.rs = da.statement.executeQuery(sql4);
            while (da.rs.next()) {
                String name = da.rs.getString(1);
                 int value = da.rs.getInt(2);
                //map1 = new HashMap<String,Integer>(); //将数据存入数列中
                //map1.put(name,value);
                //defaultdataset.addValue(rowkeys, name,value);
                 data1.add(new fp_result(da.rs.getString(1),da.rs.getInt(2)));
                //System.out.println(data1+","+data2);

            }
            da.rs.close();
            da.statement.close();
            da.connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //return data1;
    }*/

    public  List<fp_result> queryData(){
        database da = new database();
        da.initmysql();
        //查看fp_result
        String sql4 = "select `产品名称`,value from fp_result";
        //fp_growth类的集合
        List<fp_result> list = new ArrayList<fp_result>();
        try {
            da.rs = da.statement.executeQuery(sql4);
            while (da.rs.next()) {
                list.add(new fp_result(da.rs.getString(1),da.rs.getInt(2)));
            }
            da.rs.close();
            da.statement.close();
            da.connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }


    //构造柱状图
    public draw()
    {   //柱状图
        CategoryDataset dataset=getDataSet();  //数据集
        JFreeChart chart=ChartFactory.createBarChart(
                "商品受欢迎程度统计图", //标题
                "商品名称",  //x轴显示标签
                "购买次数", //y轴标签
                dataset,  //数据集
                PlotOrientation.VERTICAL, //图标为水平方向
                true,  //是否显示图的比例
                false, //是否生成工具
                false  //是否产生链接

        );

        CategoryPlot plot=chart.getCategoryPlot();  //获取图表
        CategoryAxis domainAxis=plot.getDomainAxis(); //列表
        domainAxis.setLabelFont(new Font("黑体", Font.BOLD,20));//x轴标题
        //domainAxis.setTickLabelFont(new Font("黑体", Font.BOLD,20)); //垂直标题
        ValueAxis rangeAxis=plot.getRangeAxis() ; //获取柱状的值
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD,14));  //y轴名称设置
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));  //柱状的名称字体设置
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
        //chart.setBackgroundPaint(Color.lightGray);
        plot.setBackgroundPaint(Color.WHITE);

        frame=new ChartPanel(chart,true);  //构建
    }

    //柱状图的数据
    private CategoryDataset getDataSet()
    {
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        List<fp_result> list = queryData();

        for (fp_result fp_result : list) {
              dataset.addValue(fp_result.getValue(), fp_result.getName(), " ");
        }
        return dataset;
    }

    //获得整个框架的信息
    public ChartPanel getChartPanel(){
        return frame;
    }

    //显示柱状图
    public void run()
    {
        JFrame frame = new JFrame("Java数据统计图"); //窗口名称
        frame.setBounds(600, 50, 1200, 600);  //窗口边界信息
        frame.add(new draw().getChartPanel()); //加载信息
        frame.setVisible(true); //显示
    }



}