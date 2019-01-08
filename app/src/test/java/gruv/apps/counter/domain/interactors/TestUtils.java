package gruv.apps.counter.domain.interactors;

public class TestUtils {

    private static final int WAIT_TIME = 200;//ms

    public static void waitForFinish(){
        try {
            Thread.sleep(WAIT_TIME);
        } catch (Exception e) {
        }
    }
}
