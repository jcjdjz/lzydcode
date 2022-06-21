package com.bigdata;


public class fp_result  { //用于存储mysql的数据，便于统计图读取
    String name; //名称
    int value;  //值
    String value1;
    public fp_result(String name,int value)
    {
        this.name=name;
        this.value=value;
    }

    public fp_result(String name,String value)
    {
        this.name=name;
        this.value1=value;
    }

    public String getName() {

        return name;
    }

    public int getValue() {
        return value;
    }

    public String getValue1() {
        return value1;
    }
}
