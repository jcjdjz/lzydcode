package com.bigdata;


public class fp_result  { //用于存储mysql的数据，便于统计图读取
     String name; //名称
     int value;  //值
    public fp_result(String name,int value)
    {
        this.name=name;
        this.value=value;
    }

    public String getName() {

        return name;
    }

    public int getValue() {
        return value;
    }
}
