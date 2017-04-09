import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Sms;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class SMSBackend {
    private static final String TWILIO_USERNAME = "AC6c6cf7b97b56cadcc269af4fdcb9851f";
    private static final String TWILIO_PASS = "";
    private static final String TWILIO_NUMBER = "+14692084434";

    public static void main(String[] args) {
        //get("/", (req, res) -> "Hello, World!");

        TwilioRestClient client = new TwilioRestClient(TWILIO_USERNAME, TWILIO_PASS);

        post("/sms", (req, res) -> {
            String body = req.queryParams("Body");
            String to = req.queryParams("To");
            String from = TWILIO_NUMBER;

            String result = sendMessage(client, body,to, from);
            System.out.println("result is: "+result);
            return result;
        });
    }


    public static String sendMessage(TwilioRestClient client, String body, String to, String from){
        Map<String, String> callParams = new HashMap<>();
        callParams.put("To", to);
        callParams.put("From", from);
        callParams.put("Body", body);
        Sms message = null;
        try {
            message = client.getAccount().getSmsFactory().create(callParams);
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
        return message.getSid();
    }
}
