import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class App {
 public static void main(String[] args) {
   staticFileLocation("/public");
   String layout = "templates/layout.vtl";

   get("/", (request, response) -> {
     Map<String, Object> model = new HashMap<String, Object>();
     model.put("tasks", request.session().attribute("tasks"));
     model.put("template", "templates/index.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

   get("/categories/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/category-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/categories", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Category newCategory = new Category(name);
      model.put("template", "templates/category-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

   post("/tasks", (request, response) -> {
     HashMap<String, Object> model = new HashMap<String, Object>();

     Category category = Category.find(Integer.parseInt(request.queryParams("categoryId")));

     String description = request.queryParams("description");

     Task newTask = new Task(description, category.getId());
     newTask.save();

     model.put("category", category);
     model.put("template", "templates/category-tasks-success.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());


  post("/categories", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    String name = request.queryParams("name");
    Category newCategory = new Category(name);
    newCategory.save();
    model.put("template", "templates/category-success.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/categories", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("categories", Category.all());
    model.put("template", "templates/categories.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

 }
}
