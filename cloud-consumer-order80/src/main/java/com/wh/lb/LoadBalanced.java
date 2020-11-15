package com.wh.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 轮询算法
 */
public interface LoadBalanced {

    ServiceInstance instance(List<ServiceInstance> serviceInstances);

}
