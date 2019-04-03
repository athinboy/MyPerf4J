package cn.myperf4j.bridge;

import java.util.HashMap;

/**
 * @author fenggqc
 * @create 2019-04-03 14:42
 **/
public class Bridge {


    private static boolean stopRecord = false;

    //region Getter And Setter

    public static boolean isStopRecord() {
        return stopRecord;
    }

    public static void setStopRecord(boolean stopRecord) {
        Bridge.stopRecord = stopRecord;
    }

    // endregion
}
