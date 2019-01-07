package tme;

import java.util.Map;
import java.util.Base64;
import javax.crypto.Mac;
import java.net.URLEncoder;
import java.net.URISyntaxException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.apache.http.client.utils.URIBuilder;

/**
 * Computes TME API signature required to be send with request.
 */
public class SignatureService {

    public static String signature(String actionUrl, String appSecret, Map<String, String> sortedParams) {
        String signatureBase = signatureBase(actionUrl, sortedParams);

        return hmac(signatureBase, appSecret);
    }

    private static String signatureBase(String actionUrl, Map<String, String> sortedParams) {
        String encodedParams = encodeParams(sortedParams);

        return "POST&" + urlEncode(actionUrl) + "&" + urlEncode(encodedParams);
    }

    /**
     * Like PHP hash_hmac('sha1', $signatureBase, appSecret, true)
     *
     * @see "https://github.com/tme-dev/TME-API/blob/master/PHP/basic/using_curl.php"
     * @param signatureBase Special string with api url and request sortedParams
     * @param appSecret Secret generated in developers.tme.eu
     * @return
     */
    private static String hmac(String signatureBase, String appSecret) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(appSecret.getBytes(), "HmacSHA1");
            hmac.init(secret);
            byte[] result = hmac.doFinal(signatureBase.getBytes());

            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeyException exception) {
            exception.printStackTrace();
        }

        return "";
    }

    /**
     * Converting parameters before build signature base
     *
     * @param sortedParams Parameters from api request
     * @return
     */
    private static String encodeParams(Map<String, String> sortedParams) {
        URIBuilder builder = new URIBuilder();

        sortedParams.forEach(builder::addParameter);

        try {
            return builder
                .build()
                .toString()
                .substring(1) // Without "?"
                .replaceAll("[+]", "%20") // Encode + char
                .replaceAll("%7E", "~"); // Revert ~ char
        } catch (URISyntaxException exception) {
            exception.printStackTrace();
        }

        return "";
    }

    private static String urlEncode(String input) {
        try {
            return URLEncoder.encode(
                input,
                java.nio.charset.StandardCharsets.UTF_8.toString()
            );
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }

        return "";
    }

}
