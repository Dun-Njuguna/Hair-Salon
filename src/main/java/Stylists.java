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


  public static List<Stylists> all() {
    String sql = "SELECT id, name FROM stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylists.class);
    }
  }

  public int getId() {
   return id;
  }
  

    public static Stylists find(int id) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM stylists where id=:id";
          Stylists stylists = con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Stylists.class);
          return stylists;
        }
      }


      public List<Clients> getClients() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM clients WHERE stylistId=:id";
          return con.createQuery(sql)
            .addParameter("id", this.id)
            .executeAndFetch(Clients.class);
        }
      }

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


      public void save() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO stylists(name) VALUES (:name)";
          this.id = (int) con.createQuery(sql, true)
            .addParameter("name", this.name)
            .executeUpdate()
            .getKey();
        }
      }

      public void update(String name) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "UPDATE stylists SET name = :name WHERE id = :id";
          con.createQuery(sql)
            .addParameter("name", name)
            .addParameter("id", id)
            .executeUpdate();
        }
      }

// delete method

      public void delete() {
        try(Connection con = DB.sql2o.open()) {
        String sql = "DELETE FROM stylists WHERE id = :id;";
        con.createQuery(sql)
          .addParameter("id", id)
          .executeUpdate();
        }
      }
      
      
}