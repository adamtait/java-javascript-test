package sample2;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;





public class Mustache implements RequestHandler<Integer, String>
{

    public String handleRequest(Integer myCount, Context context)
    {
        try {
            Object result = execute();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return "complete.";
    }
    
    public static void main(String... args) throws Throwable {
        execute();
    }

    public static Object execute() throws javax.script.ScriptException, java.lang.NoSuchMethodException, java.io.FileNotFoundException
    {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("nashorn");
        engine.eval(new FileReader("sources/java/mustache.js"));
        Invocable invocable = (Invocable) engine;

        String template = "Email addresses of {{contact.name}}:\n" +
            "{{#contact.emails}}\n" +
            "- {{.}}\n" +
            "{{/contact.emails}}";

        String contactJson = "{" +
            "\"contact\": {" +
            "\"name\": \"Mr A\", \"emails\": [" +
            "\"contact@some.tld\", \"sales@some.tld\"" +
            "]}}";

        Map<String, Object> data = Map.of(
                                          "contact",
                                          Map.of( "name", "Mr A",
                                                  "emails", new ArrayList<String> (Arrays.asList("contact@some.tld", "sales@some.tld")))
                                          );

        
        //Object json = engine.eval("JSON");
        //Object data = invocable.invokeMethod(json, "parse", contactJson);

        Object mustache = engine.eval("Mustache");
        Object result = invocable.invokeMethod(mustache, "render", template, data);

        System.out.println(result);
        return result;
    }
}
