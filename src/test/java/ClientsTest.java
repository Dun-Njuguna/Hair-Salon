import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.sql2o.*;

public class ClientsTest {


      @Before
      public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/salon", "njuguna", "pass");
      }


      @After
      public void tearDown() {
        try(Connection con = DB.sql2o.open()) {
          String deleteTasksQuery = "DELETE FROM stylists *;";
          String deleteCategoriesQuery = "DELETE FROM clients *;";
          con.createQuery(deleteTasksQuery).executeUpdate();
          con.createQuery(deleteCategoriesQuery).executeUpdate();
        }
      }

    @Test
    public void Clients_instantiatesCorrectly_true() {
        Clients myClient = new Clients("Mow", 1);
        assertEquals(true, myClient instanceof Clients);
    }

    @Test
    public void Clients_instantiatesWithName_String() {
        Clients myTask = new Clients("Mow", 1);
        assertEquals("Mow", myTask.getName());
    }


      @Test
      public void all_returnsAllInstancesOfClient_true() {
        Clients firstClient = new Clients("Mow", 1);
        firstClient.save();
        Clients secondClient = new Clients("Mow", 1);
        secondClient.save();
        assertEquals(true, Clients.all().get(0).equals(firstClient));
        assertEquals(true, Clients.all().get(1).equals(secondClient));
      }


      @Test
      public void find_returnsClientWithSameId_secondClient() {
        Clients firstClient = new Clients("Mow", 1);
        firstClient.save();
        Clients secondClient = new Clients("Mow", 2);
        secondClient.save();
        assertEquals(Clients.find(secondClient.getId()), secondClient);
      }

    @Test
    public void clear_emptiesAllClientsFromArrayList_0() {
        Clients myClient = new Clients("Mow", 1);
        assertEquals(Clients.all().size(), 0);
    }


    @Test
    public void getId_clientsInstantiateWithAnID() {
      Clients myClient = new Clients("Mow", 1);
      myClient.save();
      assertTrue(myClient.getId() > 0);
    }



    @Test
    public void equals_returnsTrueIfClientsAretheSame() {
      Clients firstClient = new Clients("Mow", 1);
      Clients secondClient = new Clients("Mow", 1);
      assertTrue(firstClient.equals(secondClient));
    }


    @Test
    public void save_returnsTrueIfClientsAretheSame() {
      Clients myClient = new Clients("Mow", 1);
      myClient.save();
      assertTrue(Clients.all().get(0).equals(myClient));
    }


    @Test
    public void save_assignsIdToObject() {
      Clients myClient = new Clients("Mow", 1);
      myClient.save();
      Clients savedClient = Clients.all().get(0);
      assertEquals(myClient.getId(), savedClient.getId());
    }


      @Test
      public void save_savesClientsIdIntoDB_true() {
        Stylists myStylist = new Stylists("Household chores");
        myStylist.save();
        Clients myClient = new Clients("Mow", myStylist.getId());
        myClient.save();
        Clients savedClient = Clients.find(myClient.getId());
        assertEquals(savedClient.getStylistId(), myStylist.getId());
      }

      @Test
      public void update_updatesClientDescription_true() {
        Clients myClients= new Clients("Mow", 1);
        myClients.save();
        myClients.update("Mows");
        assertEquals("Mows", Clients.find(myClients.getId()).getName());
      }


}
