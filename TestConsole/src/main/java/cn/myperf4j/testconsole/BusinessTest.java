package cn.myperf4j.testconsole;

/**
 * @author fenggqc
 * @create 2019-04-10 13:54
 **/

public class BusinessTest {

    //region Getter And Setter
    // endregion

    public void Run1() throws InterruptedException {


        Long l=0L;
        A(l);
        System.out.println(l);


        Thread.sleep(10000);
    }

    public void A(Long l) {
        l = 1L;
    }

}
