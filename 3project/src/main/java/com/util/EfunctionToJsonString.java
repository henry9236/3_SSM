package com.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bean.Efunction;

import java.util.List;

public class EfunctionToJsonString {

    /**
     * 查看List里是否有 子 元素
     * 传进父元素id，看看parentid有没有对应的
     * @param listE
     * @param parentid
     * @return 真假值，是否有子元素
     */
    public static boolean ListContains(List<Efunction> listE, long parentid){
        for (Efunction ef:listE
        ) {
            if (ef.getParentid()==parentid){
                return true;
            }
        }
        return false;
    }

    /**
     * 通过递归把list<Efunction>放入JSONArray返回
     * @param listE Efunction的List集合
     * @param parentid 父id
     * @return
     */
    public static JSONArray eFunctionLoop(List<Efunction> listE, long parentid){
        //把没有子元素的变成JSONArray 设置 state open 返回，结束递归
        if (!ListContains(listE,parentid)){
            JSONArray jsonArray = new JSONArray();
            for (Efunction ef: listE) {
                if(ef.getFid()==parentid){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id",ef.getFid());
                    jsonObject.put("text",ef.getFname());
                    jsonObject.put("state","open");
                    jsonObject.put("url",ef.getUrl());
                    jsonArray.add(jsonObject);
                }
            }
            return jsonArray;
        }
        //如果有子元素，则执行递归
        JSONArray jsonArray = new JSONArray();
        for (Efunction ef: listE) {
            if(ef.getParentid()==parentid){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",ef.getFid());
                jsonObject.put("text",ef.getFname());
                jsonObject.put("state","closed");
                jsonObject.put("children",eFunctionLoop(listE,ef.getFid()));
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }
}
