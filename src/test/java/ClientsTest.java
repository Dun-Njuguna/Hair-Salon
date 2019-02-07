import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.sql2o.*;

public class Clientsest {

// creates a new connection to the database before every test
      @Before
      public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/salon", "njuguna", "pass");
      }

// clears the database after every test
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
        assertEquals("Mow the lawn", myTask.getName());
    }

// returns all data in database while still saving using sava()
      @Test
      public void all_returnsAllInstancesOfClient_true() {
        Clients firstClient = new Clients("Mow", 1);
        firstClient.save();
        Clients secondClient = new Clients("Mow", 1);
        secondClient.save();
        assertEquals(true, Clients.all().get(0).equals(firstClient));
        assertEquals(true, Clients.all().get(1).equals(secondClient));
      }

// Our Task.find(myTask.getId()) uses the Task's id to query the database and return a Task object.
      @Test
      public void find_returnsClientWithSameId_secondClient() {
        Client firstClient = new Client("Mow", 1);
        firstClient.save();
        Client secondClient = new Client("Mow", 2);
        secondTask.save();
        assertEquals(Client.find(secondTask.getId()), secondTask);
      }

    @Test
    public void clear_emptiesAllClientsFromArrayList_0() {
        Clients myClient = new Clients("Mow", 1);
        assertEquals(Clients.all().size(), 0);
    }

// this test checks that the id is greater than 0 from DB
    @Test
    public void getId_clientsInstantiateWithAnID() {
      Clients myClient = new Clients("Mow", 1);
      myClient.save();
      assertTrue(myClient.getId() > 0);
    }


// a test to check whether to instances of info retriieved from the database are the same 
    @Test
    public void equals_returnsTrueIfClientsAretheSame() {
      Clients firstClient = new Clients("Mow", 1);
      Clients secondClient = new Clients("Mow", 1);
      assertTrue(firstClient.equals(secondClient));
    }

// saving objects into the database
    @Test
    public void save_returnsTrueIfClientsAretheSame() {
      Clients myClient = new Clients("Mow", 1);
      myClient.save();
      assertTrue(Clients.all().get(0).equals(myClient));
    }

// test ids of tasks in database and application
    @Test
    public void save_assignsIdToObject() {
      Clients myClient = new Clients("Mow", 1);
      myClient.save();
      Clients savedClient = Clients.all().get(0);
      assertEquals(myClient.getId(), savedClient.getId());
    }

// Next, we'll write a unit test that ensures 
//we can save a categoryId into our tasks table, 
//thereby associating a Task with its Category:
      @Test
      public void save_savesClientsIdIntoDB_true() {
        Stylists myStylist = new Stylists("Household chores");
        myStylist.save();
        Clients myClient = new Clients("Mow", myStylist.getId());
        myClient.save();
        Clients savedClient = Clients.find(myClient.getId());
        assertEquals(savedClient.getStylistId(), myClient.getId());
      }

}
