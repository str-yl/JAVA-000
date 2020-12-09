package com.example.job01.service;

import com.example.job01.entity.t_order;

import java.util.List;

public interface OrderService {

    Long addOrder(t_order user);

    List<t_order> list();

    boolean deleteByOrderId(int order_id);

    boolean updateProduct_cntByOrder_id(t_order user);

    boolean addBatchOrders(List<t_order> orders) throws Exception;
}
