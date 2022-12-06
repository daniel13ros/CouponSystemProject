
import utils.TestUtils;

/**
 * Created by danielR on 27 oct, 2022
 */


public class Application {

    public static void main(String[] args)  {
        try {
            TestUtils.testAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
