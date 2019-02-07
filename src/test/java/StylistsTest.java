import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistsTest {

      @Before
      public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/salon", "njuguna", "pass");
      }

      @After
      public void tearDown() {
        try(Connection con = DB.sql2o.open()) {
          String deleteStylistsQuery = "DELETE FROM stylists *;";
          String deleteClientsQuery = "DELETE FROM clients *;";
          con.createQuery(deleteStylistsQuery).executeUpdate();
          con.createQuery(deleteClientsQuery).executeUpdate();
        }
      }

  @Test
  public void stylists_instantiatesCorrectly_true() {
    Stylists testStylists= new Stylists("Peter");
    assertEquals(true, testStylists instanceof Stylists);
  }

      @Test
  public void getName_stylistsInstantiatesWithName_Peter() {
    Stylists testStylists= new Stylists("Peter");
    assertEquals("Peter", testStylists.getName());
  }


      @Test
      public void all_returnsAllInstancesOfCategory_true() {
        Stylists firstStylists = new Stylists("Peter");
        firstStylists.save();
        Stylists secondStylists = new Stylists("Mark");
        secondStylists.save();
        assertEquals(true, Stylists.all().get(0).equals(firstStylists));
        assertEquals(true, Stylists.all().get(1).equals(secondStylists));
      }


     @Test
     public void getId_stylistsInstantiateWithAnId_1() {
       Stylists testStylists = new Stylists("Peter");
       testStylists.save();
       assertTrue(testStylists.getId() > 0);
     }

	@Test
	public void getClients_initiallyReturnsEmptyList_ArrayList() {
	  Stylists testStylists = new Stylists("Mark");
	  assertEquals(0, testStylists.getClients().size());
  }


     @Test
     public void find_returnsStylistsWithSameId_secondStylists() {
       Stylists firstStylists = new Stylists("Peter");
       firstStylists.save();
       Stylists secondStylists = new Stylists("Mark");
       secondStylists.save();
       assertEquals(Stylists.find(secondStylists.getId()), secondStylists);
     }

      @Test
      public void equals_returnsTrueIfNamesAretheSame() {
        Stylists firstStylists = new Stylists("Mark");
        Stylists secondStylists = new Stylists("Mark");
        assertTrue(firstStylists.equals(secondStylists));
      }


      @Test
      public void save_savesIntoDatabase_true() {
        Stylists myStylists = new Stylists("Mark");
        myStylists.save();
        assertTrue(Stylists.all().get(0).equals(myStylists));
      }

      @Test
      public void save_assignsIdToObject() {
        Stylists myStylists = new Stylists("Mark");
        myStylists.save();
        Stylists savedStylists = Stylists.all().get(0);
        assertEquals(myStylists.getId(), savedStylists.getId());
      }


      @Test
      public void getClients_retrievesALlClientsFromDatabase_clientsList() {
        Stylists myStylists= new Stylists("Mark");
        myStylists.save();
        Clients firstClient = new Clients("Peter", myStylists.getId());
        firstClient.save();
        Clients secondClient = new Clients("DUN", myStylists.getId());
        secondClient.save();
        Clients[] clients = new Clients[] { firstClient, secondClient };
        assertTrue(myStylists.getClients().containsAll(Arrays.asList(clients)));
      }

}
