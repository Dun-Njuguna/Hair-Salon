import java.util.List;
import java.util.Arrays;
import org.sql2o.*;

public class Stylists {
  private String name;
  private int id;

  public Stylists(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

// Here, we construct a basic SQL query requesting all id and description data from the styliststable.
  public static List<Stylists> all() {
    String sql = "SELECT id, name FROM stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylists.class);
    }
  }

  public int getId() {
   return id;
  }
  
// To make it pass (find test), we can re-populate our static find() method with code to locate a specific Stylists:
// Here we are using a select query using where id=:id. 
// We use .addParameter("id", id)to pass in the id argument to the sql query 
// and then we run .executeAndFetchFirst(Stylists.class);. 
// This will return the first item in the collection returned by our database, 
// cast as a Stylists object. Finally, we return that Stylists.
    public static Category find(int id) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM stylists where id=:id";
          Stylists stylists = con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Stylists.class);
          return stylists;
        }
      }

// To make a list of our in memory objects, we first construct a task array containing those objects. 
// We then use a new method Arrays.asList(tasks) to save those items into a list. 
// (You will need to add import java.util.Arrays; to the top of the file to use Arrays.asList.)
      public List<Clients> getClients() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM clients WHERE stylistId=:id";
          return con.createQuery(sql)
            .addParameter("id", this.id)
            .executeAndFetch(Clients.class);
        }
      }
// equals() method 
// We should also modify our equals() method to account for this new property:(ids)
      @Override
      public boolean equals(Object otherStylists) {
        if (!(otherStylists instanceof Stylists)) {
          return false;
        } else {
          Stylists newStylists = (Stylists) otherStylists;
          return this.getName().equals(newStylists.getName()) &&
                 this.getId() == newStylists.getId();
        }
      }

// .save() method
// we'll use save() to assign the object the same id as its data in the database:
      public void save() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO stylists(name) VALUES (:name)";
          this.id = (int) con.createQuery(sql, true)
            .addParameter("name", this.name)
            .executeUpdate()
            .getKey();
        }
      }
}