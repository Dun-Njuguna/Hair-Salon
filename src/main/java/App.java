import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("stylists", Stylists.all());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/stylists", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          String name = request.queryParams("name");
          Stylists newStylists = new Stylists(name);
          newStylists.save();
          model.put("template", "templates/index.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/clients", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
        
          Stylists stylists = Stylists.find(Integer.parseInt(request.queryParams("stylistId")));
        
          String name = request.queryParams("name");
          Clients newClients = new Clients(name, stylists.getId());
        
          newClients.save();
        
          model.put("stylists", stylists);
          model.put("template", "templates/index.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/clients/:id", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          Stylists stylists = Stylists.find(Integer.parseInt(request.params(":id")));
          model.put("stylists", stylists);
          model.put("template", "templates/clients.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        
        // ProcessBuilder process = new ProcessBuilder();
        //  Integer port;
        //  if (process.environment().get("PORT") != null) {
        //      port = Integer.parseInt(process.environment().get("PORT"));
        //  } else {
        //      port = 4567;
        //  }
    
        // setPort(port);
    }
}