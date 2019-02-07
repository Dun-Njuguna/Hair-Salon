    import java.util.ArrayList;
    import java.util.List;
    import org.sql2o.*;
    

public class Clients {
    private String name;
    private int id;
    private int stylistId;


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


      public static List<Clients> all() {
        String sql = "SELECT id, name, stylistId FROM clients";
        try(Connection con = DB.sql2o.open()) {
         return con.createQuery(sql).executeAndFetch(Clients.class);
        }
      }

    public static Clients find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM clients where id=:id";
        Clients clients = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Clients.class);
        return clients;
      }
    }


      @Override
      public boolean equals(Object otherClient){
        if (!(otherClient instanceof Clients)) {
          return false;
        } else {
          Clients newClient = (Clients) otherClient;
          return this.getName().equals(newClient.getName()) &&
                 this.getId() == newClient.getId() &&
                 this.getStylistId() == newClient.getStylistId();
        }
      }


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

      public void update(String name) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "UPDATE clients SET name = :name WHERE id = :id";
          con.createQuery(sql)
            .addParameter("name", name)
            .addParameter("id", id)
            .executeUpdate();
        }
      }


}