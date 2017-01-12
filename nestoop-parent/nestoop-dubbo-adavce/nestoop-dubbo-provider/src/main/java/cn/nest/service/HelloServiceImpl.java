package cn.nest.service;

import cn.nest.interfaces.IHelloService;

/**
 * Created by botter
 *
 * @Date 6/1/17.
 * @description
 */
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String say) {
        System.out.println("say............");
        return "bottter say: " + say;
    }
}
