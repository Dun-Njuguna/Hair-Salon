    import java.util.ArrayList;
    import java.util.List;
    import org.sql2o.*;
    

public class Clients {
    private String name;
    private int id;
    private int stylistId;

// alter Task constructor to require a Category ID.
    public Clients(String name, int stylistId) {
        this.name = name;
        this.stylistId = stylistId;
      }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getStylistId() {
      return stylistId;
    }

// getting from database and saving in a list
//  update our Task.all() method's SQL query to include stylistsId
      public static List<Clients> all() {
        String sql = "SELECT id, name, categoryId FROM clients";
        try(Connection con = DB.sql2o.open()) {
         return con.createQuery(sql).executeAndFetch(Task.class);
        }
      }

// To make this test(find) pass, we'll re-populate our static find() method with code to locate a specific Taskin our database:
    public static Clients find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM clients where id=:id";
        Task task = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Task.class);
        return task;
      }
    }

//Overriding equals()
//update .equals() method 
//to take this property (category id)into account when comparing Taskobjects:
      @Override
      public boolean equals(Object otherClient){
        if (!(otherClient instanceof Clients)) {
          return false;
        } else {
          Clients newClient = (Clients) otherTask;
          return this.getName().equals(newClient.getName()) &&
                 this.getId() == newClient.getId() &&
                 this.getStylistId() == newClient.getStylistId();
        }
      }

// method to store objects in database
//  we need to include the categoryId in the SQL statement within our save() method. 
// This will ensure the categoryId is saved in our database:
      public void save() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO clients(name, stylistId) VALUES (:name, :stylistId)";
          this.id = (int) con.createQuery(sql, true)
            .addParameter("name", this.name)
            .addParameter("stylistId", this.stylistId)
            .executeUpdate()
            .getKey();
        }
      }


}