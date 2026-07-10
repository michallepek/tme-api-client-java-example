This repository is maintained for the **deprecated version of the TME API only** and is no longer updated with the latest API documentation.

For the current API version (v2), please refer to the official documentation available:
- via the [**Developer Portal**][developers-website]
- directly at [**documentation page**][documentation-website]

To ensure you are using the latest endpoints, authentication flow, and integration guidelines, always refer to the official TME API v2 documentation.

[developers-website]: https://developers.tme.eu
[documentation-website]: https://api-doc.tme.eu

# tme-api-client-java-example

Simple TME API Client written in Java.

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
