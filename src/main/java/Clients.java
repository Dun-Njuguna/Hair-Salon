import java.util.List;
import java.util.Arrays;
import org.sql2o.*;

public class Clients {
  private String name;
  private int id;

  public Clients(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
   return id;
  }

// Here, we construct a basic SQL query requesting all id and description data from the clientstable.
  public static List<Category> all() {
    String sql = "SELECT id, name FROM clients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }
