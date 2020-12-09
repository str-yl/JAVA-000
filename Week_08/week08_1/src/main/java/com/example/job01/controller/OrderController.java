package com.example.job01.controller;

import com.example.job01.entity.t_order;
import com.example.job01.service.OrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("订单管理")
@RestController
@RequestMapping("/op/*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("获取订单列表")
    @GetMapping("/showOrders")
    public Object list() {
        return orderService.list();
    }

    @ApiOperation("Swagger添加订单示例")
    @ApiResponses({
            @ApiResponse(code = 404, response = String.class, message = "order_add failed"),
            @ApiResponse(code = 500, response = String.class, message = "Internal server error")
    })
//    @GetMapping("/addOrder")
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@ApiParam(name = "order_id",value = "传入订单ID") int order_id,
                      @ApiParam(name = "customer_id",value = "传入买家ID") int customer_id,
                      @ApiParam(name = "product_id",value = "传入产品ID") int  product_id,
                      @ApiParam(name = "product_name",value = "传入产品名称") String  product_name,
                      @ApiParam(name = "product_cnt",value = "传入产品数量") int  product_cnt,
                      @ApiParam(name = "product_price",value = "传入产品单价") double  product_price,
                      @ApiParam(name = "shipping_user",value = "传入卖家name") String  shipping_user) {
        t_order o = new t_order(order_id, customer_id, product_id,
                product_name,product_cnt,product_price,shipping_user);
        return orderService.addOrder(o);
    }

    @ApiOperation("批量添加订单")
    @ApiImplicitParam(name = "t_orders", value = "N个订单信息", dataType = "List<t_order>")
    @GetMapping("/addOrders")
    public void addOrdersByBatch(List<t_order> orders) throws Exception{

        orderService.addBatchOrders(orders);
    }
}
