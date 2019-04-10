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

    
        cn.myperf4j.bridge.Bridge.setStopRecord(true);

        
        cn.myperf4j.bridge.Bridge.setStopRecord(false);
    
 
        System.out.println("exit");

        System.exit(0);

    }

}
