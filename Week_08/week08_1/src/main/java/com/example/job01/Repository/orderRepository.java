package com.example.job01.Repository;

import com.example.job01.entity.t_order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface orderRepository {

    Long addOrder(t_order user);

    List<t_order> list();

    boolean deleteByOrderId(int order_id);

    boolean updateProduct_cntByOrder_id(t_order user);

    boolean addBatchOrders(List<t_order> orders);

}
