import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        ProcessBuilder process = new ProcessBuilder();
         Integer port;
         if (process.environment().get("PORT") != null) {
             port = Integer.parseInt(process.environment().get("PORT"));
         } else {
             port = 4567;
         }
    
        setPort(port);

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
          model.put("template", "templates/success.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/clients", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
        
          Stylists stylists = Stylists.find(Integer.parseInt(request.queryParams("stylistId")));
        
          String name = request.queryParams("name");
          Clients newClients = new Clients(name, stylists.getId());
        
          newClients.save();
        
          model.put("stylists", stylists);
          model.put("template", "templates/success.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/clients/:id", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          Stylists stylists = Stylists.find(Integer.parseInt(request.params(":id")));
          model.put("stylists", stylists);
          model.put("template", "templates/clients.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/clients", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("clients", Clients.all());
          model.put("template", "templates/all_clients.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylist", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          model.put("stylists", Stylists.all());
          model.put("template", "templates/all_stylists.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
  // update client
        get("/stylists/:stylists_id/clients/:id", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        Stylists stylists = Stylists.find(Integer.parseInt(request.params(":stylists_id")));
        Clients clients = Clients.find(Integer.parseInt(request.params(":id")));
        model.put("stylists", stylists);
        model.put("clients", clients);
        model.put("template", "templates/update_client.vtl");
        return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        post("/stylists/:stylists_id/clients/:id", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          Clients clients = Clients.find(Integer.parseInt(request.params("id")));
          String name = request.queryParams("name");
          Stylists stylists = Stylists.find(clients.getStylistId());
          clients.update(name);
          String url = String.format("/stylists/%d/clients/%d", stylists.getId(), clients.getId());
          response.redirect(url);
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylists/:id", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          Stylists stylists = Stylists.find(Integer.parseInt(request.params(":id")));
          model.put("stylists", stylists);
          model.put("template", "templates/clients.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


// update stylist

        get("/stylist/update", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("stylists", Stylists.all());
        model.put("template", "templates/update_stylist.vtl");
        return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylists/update/:id", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          Stylists stylists = Stylists.find(Integer.parseInt(request.params(":id")));
          model.put("stylists", stylists);
          model.put("template", "templates/update_stylist_details.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());        

        post("/stylists/:id", (request, response) -> {
          Map<String, Object> model = new HashMap<String, Object>();
          Stylists stylists = Stylists.find(Integer.parseInt(request.params("id")));
          String name = request.queryParams("name");
          Stylists newStylists = Stylists.find(stylists.getId());
          stylists.update(name);
          String url = String.format("/stylists/%d", newStylists.getId());
          response.redirect(url);
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

// delete client

        post("/client/:stylists_id/clients/:id/delete", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          Clients clients = Clients.find(Integer.parseInt(request.params("id")));
          Stylists stylists = Stylists.find(clients.getStylistId());
          clients.delete();
          model.put("stylists", stylists);
          model.put("template", "templates/update_client.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

// delete stylists

        post("/stylists/:id/delete", (request, response) -> {
          HashMap<String, Object> model = new HashMap<String, Object>();
          Stylists stylists = Stylists.find(Integer.parseInt(request.params("id")));
          stylists.delete();
          model.put("stylists", stylists);
          model.put("template", "templates/update_stylist_details.vtl");
          return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


    }
}