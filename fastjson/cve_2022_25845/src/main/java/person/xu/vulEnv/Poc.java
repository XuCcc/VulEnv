package person.xu.vulEnv;

import java.io.IOException;

public class Poc extends Throwable {
    public void setName(String str) {
        try {
            Runtime.getRuntime().exec(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
