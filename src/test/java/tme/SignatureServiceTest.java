package tme;

import java.util.Map;
import org.junit.Test;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class SignatureServiceTest {

    @Test
    public void testSignature() {
        String actionUrl = "https://api.tme.eu/Products/Search.json";
        String appSecret = "TEST_SECRET";

        Map<String, String> params = new TreeMap<>();
        params.put("Token", "TEST_TOKEN");
        params.put("SearchPlain", "multimetr");
        params.put("SearchCategory", "100164");
        params.put("SearchParameter[2][0]", "386527");
        params.put("SearchParameter[2][1]", "15");
        params.put("Country", "pl");
        params.put("Language", "pl");

        String actual = SignatureService.signature(actionUrl, appSecret, params);

        assertEquals(
            "Generate signature for GetPrices API action and array of symbols",
            "qnvlqHSHb0/3rqvD0A9O8ADolxY=",
            actual
        );
    }

}
