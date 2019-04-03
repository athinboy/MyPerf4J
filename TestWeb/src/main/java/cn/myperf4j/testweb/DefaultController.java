package cn.myperf4j.testweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author fenggqc
 * @create 2019-04-03 19:26
 **/
@Controller
@RequestMapping("/d/")
public class DefaultController {

    //region Getter And Setter
    // endregion

    @ResponseBody
    @RequestMapping("index")
    public Object index() {
        return new Date();
    }

    @ResponseBody
    @RequestMapping("stoprecord")
    public String stoprecord() {

        cn.myperf4j.bridge.Bridge.setStopRecord(true);
        return "";
    }

    @ResponseBody
    @RequestMapping("startrecord")
    public String startrecord() {

        cn.myperf4j.bridge.Bridge.setStopRecord(false);
        return "";
    }

}
