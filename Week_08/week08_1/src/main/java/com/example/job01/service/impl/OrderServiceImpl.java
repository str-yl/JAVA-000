package com.example.job01.service.impl;

import com.example.job01.entity.t_order;
import com.example.job01.service.OrderService;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    com.example.job01.Repository.orderRepository or;

    @Override
    public Long addOrder(t_order user) {
        return or.addOrder(user);
    }

    @Override
    public List<t_order> list() {
        return or.list();
    }

    @Override
    public boolean deleteByOrderId(int order_id) {
        return or.deleteByOrderId(order_id);
    }

    @Override
    public boolean updateProduct_cntByOrder_id(t_order user) {
        return or.updateProduct_cntByOrder_id(user);
    }

    @Override
    @ShardingTransactionType(TransactionType.XA)
    @Transactional(rollbackFor=Exception.class)
    public boolean addBatchOrders(List<t_order> orders) throws Exception{
        boolean res = false;
        res = or.addBatchOrders(orders);
        if(!res){
            throw new Exception("批量添加订单失败");
        }
        return res;
    }
}
