package com.example.job01.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(value="订单对象", description="订单对象order")
@Data
@AllArgsConstructor
public class t_order {

    @ApiModelProperty(value = "主键ID")
    private int order_id;

    @ApiModelProperty(value = "买家ID",required = true)
    private int customer_id;

    @ApiModelProperty(value = "产品ID",required = true)
    private int product_id;

    @ApiModelProperty(value = "产品名称",required = true)
    private String product_name;

    @ApiModelProperty(value = "产品数量",required = true)
    private int product_cnt;

    @ApiModelProperty(value = "产品单价",required = true)
    private double product_price;

    @ApiModelProperty(value = "卖家name")
    private String shipping_user;

}
