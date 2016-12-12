package cn.nest;

import org.springframework.stereotype.Component;

/**
 * Created by botter
 *
 * @Date 9/12/16.
 * @description
 */
@Component
public class Counter {

    private volatile int i = 1;

    public void add(int num) {
        i = num + 1;
    }

    public int getI(){
        return i;
    }
}
