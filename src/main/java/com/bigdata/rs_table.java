package com.bigdata;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class rs_table {
    database da=new database();
    public void show()
    {
        JFrame jFrame=new JFrame();  //弹窗
        JTable jTable=new JTable();  //弹窗表格
        DefaultTableModel dt=(DefaultTableModel) jTable.getModel(); //表格的模型
        String sql4="select `产品名称`,value from fp_result";

        da.initmysql();
        try {
            da.rs1 = da.statement.executeQuery(sql4);
            List<fp_result> list=new ArrayList<>();  //存储表的数据
            while (da.rs1.next()){
                list.add(new fp_result(da.rs1.getString(1),da.rs1.getInt(2)));
            }
            dt.addColumn("产品名称");
            dt.addColumn("value");
            for(fp_result fp:list)
            {
                Object[] objects={fp.getName(),fp.getValue()};  //读取表的数据
                dt.addRow(objects);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


        jFrame.add(jTable);
        jFrame.setBounds(200,0,300,1000);
        jFrame.setVisible(true);
    }

    public void show1()
    {
        JFrame jFrame=new JFrame();  //弹窗
        JTable jTable=new JTable();  //弹窗表格
        DefaultTableModel dt=(DefaultTableModel) jTable.getModel(); //表格的模型
        String sql4="select `产品名称`,value from confidence_result";

        da.initmysql();
        try {
            da.rs1 = da.statement.executeQuery(sql4);
            List<fp_result> list=new ArrayList<>();  //存储表的数据
            while (da.rs1.next()){
                list.add(new fp_result(da.rs1.getString(1),da.rs1.getString(2)));
            }
            dt.addColumn("产品名称");
            dt.addColumn("value");
            for(fp_result fp:list)
            {
                Object[] objects={fp.getName(),fp.getValue1()};  //读取表的数据
                dt.addRow(objects);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        jFrame.add(jTable);
        jFrame.setBounds(500,200,300,150);
        jFrame.setVisible(true);

    }

}
