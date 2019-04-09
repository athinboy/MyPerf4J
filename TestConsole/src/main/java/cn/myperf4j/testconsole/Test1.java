package cn.myperf4j.testconsole;

import java.io.Console;
import java.io.IOException;

/**
 * @author fenggqc
 * @create 2019-04-03 20:44
 **/

public class Test1 {

    //region Getter And Setter
    // endregion

    public static void main(String[] args) throws IOException {

        int s = System.in.read();
        cn.myperf4j.bridge.Bridge.setStopRecord(true);

        System.out.println(s);
        s = System.in.read();

        cn.myperf4j.bridge.Bridge.setStopRecord(false);
        System.out.println(s);

        s = System.in.read();
        System.out.println(s);

    }

}
