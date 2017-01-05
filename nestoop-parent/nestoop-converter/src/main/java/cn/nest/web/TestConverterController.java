package cn.nest.web;

import cn.nest.model.Vscode;
import org.springframework.web.bind.annotation.*;

/**
 * Created by botter
 *
 * @Date 4/1/17.
 * @description
 */
@RestController
public class TestConverterController {

    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public @ResponseBody  String testJSONConverter(@RequestBody Vscode code) {
        System.out.println("==========" + code.getVisaCode());
        return "ok";
    }

    @RequestMapping(value = "/xml", method = RequestMethod.POST)
    public @ResponseBody  String testXMLConverter(@RequestBody Vscode code) {
        System.out.println("==========" + code.getVisaCode());
        return "ok";
    }
}
