package cn.nest.web;

import org.springframework.web.bind.annotation.*;

/**
 * Created by botter
 *
 * @Date 4/1/17.
 * @description
 */
@RestController
public class TestConverterController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public @ResponseBody  String testConverter(@RequestBody String code) {
        System.out.println("code ===: " + code);
        return "ok";
    }
}
