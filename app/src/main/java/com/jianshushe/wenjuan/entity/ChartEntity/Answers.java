package com.jianshushe.wenjuan.entity.ChartEntity;

import java.io.Serializable;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.entity.ChartEntity
 * 文件名：Answers
 * 创建者：jiushaba
 * 创建时间：2018/1/4 0004上午 11:43
 * 描述： TODO
 */
public class Answers implements Serializable {
   private int type;
   private int num;
   private String optionName;

   public int getType() {
      return type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getNum() {
      return num;
   }

   public void setNum(int num) {
      this.num = num;
   }

   public String getOptionName() {
      return optionName;
   }

   public void setOptionName(String optionName) {
      this.optionName = optionName;
   }
}