# tme-api-client-java-example

Simple TME API Client written in Java.

* https://developers.tme.eu/en/
* https://tme.eu/

```Java
package example;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import java.io.IOException;

/**
 * API TME example request
 */
public class App {

    private static final String token = "<PUT_YOUR_TOKEN>";
    private static final String secret = "<PUT_YOUR_SECRET>";
    private static final String apiActionUrl = "https://api.tme.eu/Products/GetPrices.json";

    public static void main(String[] args) {
        try {
            Map<String, String> params = new TreeMap<>();
            params.put("Token", token);
            params.put("SymbolList[0]", "1N4007-DC");
            params.put("Country", "PL");
            params.put("Currency", "PLN");
            params.put("Language", "PL");

            String signature = SignatureService.signature(apiActionUrl, secret, params);
            params.put("ApiSignature", signature);

            String response = HttpService.sendPost(new URL(apiActionUrl), params);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
```

## License

MIT
