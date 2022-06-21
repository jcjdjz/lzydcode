package com.bigdata;

import java.sql.*;

public class database {
    //数据库连接三元组
    public static String url="jdbc:mysql:///bigdata?characterEncoding=utf8"; //数据库路径
    public static String user="root"; //用户名
    public static String password="147896"; //密码

    Connection connection;  //连接数据库
    public Statement statement;  //输出器
    public ResultSet rs; //结果集
    ResultSet rs1;
    public String name;
    public String value;
    public void initmysql()
    {
        //注册驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection(url,user,password); //连接数据库
            //获取传输器，执行sql命令
            statement=connection.createStatement();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void begin()  {
        initmysql();
        //创建分组合并表
        //String sql="create table result1 select `客户ID`,`订单日期`,group_concat(`产品名称`) as `产品名称` from excel group by `客户ID`,`订单日期`";
        String sql="create table result2 select `客户ID`,`订单日期`,group_concat(distinct `产品名称`) as `产品名称` from excel group by `客户ID`,`订单日期`";
        //创建训练数据表
        String sql1="create table exercise_bef select `产品名称` from result2";
        //创建exercise的txt文件
        String sql2="select `产品名称` from exercise_bef into outfile 'F:exercise_bef.txt' ";
        /*try {
            statement.executeQuery(sql2);
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        //去除重复数据
        String sql3="create table exercise select distinct `产品名称` from exercise_bef";
        //查看fp_result
        String sql4="select `产品名称`,value from fp_result";
        //执行sql
       /* try {
            rs = statement.executeQuery(sql4);
            while (rs.next()) {
                name = rs.getString(1);
                value = rs.getString(2);
                System.out.println(name + "," + value);
                //System.out.println(name + "," + value);
                //System.out.println(rs.getString(1));
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }*/
        //创建表
        //statement.executeUpdate(sql3);
        //输出结果集
        /*while(rs.next()) //判断是否还有数据，有继续执行
        {
            String date=rs.getString(1);
            System.out.println(date);
        }*/
        //rs.close();
        try {
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void paste(String id, String value) throws SQLException {
        initmysql();
        String tableName="fp_result";  //表名

        //建表
        StringBuilder createTable=new StringBuilder();
       //DatabaseMetaData dbMetaData=connection.getMetaData();
        //String[]   types   =   { "TABLE" };
       // rs1=dbMetaData.getTables(null, null, tableName, types);
        /*if(rs1.next()){
            statement.executeUpdate("drop table fp_result"); //删除存在的表
        }*/
        //else {
            createTable.append("create table " + tableName + "(");
            createTable.append("`产品名称`" + "varchar(255) not null,");
            createTable.append("value int not null);");
            //statement.executeUpdate(createTable.toString());
        //}
            //导入数据
            StringBuilder insertQuery = new StringBuilder();
        /*if(insertQuery!=null) {
            StringBuilder insertQuery1=new StringBuilder("truncate fp_result");//如果有内容先删掉
            statement.executeUpdate(insertQuery1.toString());
        }*/
            insertQuery.append("insert into fp_result values(");
            insertQuery.append("'" + id + "'" + "," + value);
            insertQuery.append(");");
            statement.executeUpdate(insertQuery.toString());
    }

    public void paste1(String id, String value) throws SQLException {
        initmysql();
        String tableName="confidence_result";  //表名

        //建表
        StringBuilder createTable=new StringBuilder();
        /*if(createTable!=null){
            statement.executeUpdate("drop table fp_result"); //删除存在的表
        }*/
        createTable.append("create table "+tableName+"(");
        createTable.append("`产品名称`"+"varchar(255) not null,");
        createTable.append("value double(10,3) not null);");
        //statement.executeUpdate(createTable.toString());

        //导入数据
        StringBuilder insertQuery=new StringBuilder();
        /*if(insertQuery!=null) {
            StringBuilder insertQuery1=new StringBuilder("truncate confidence_result");//如果有内容先删掉
            statement.executeUpdate(insertQuery1.toString());
        }*/
        insertQuery.append("insert into confidence_result values(");
        insertQuery.append("'" + id + "'" + "," + value);
        insertQuery.append(");");
        statement.executeUpdate(insertQuery.toString());
    }

}
