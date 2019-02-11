import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistsTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

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

      @Test
      public void update_updatesStylistsDescription_true() {
        Stylists myStylists = new Stylists("Mow");
        myStylists.save();
        myStylists.update("Mows");
        assertEquals("Mows", Stylists.find(myStylists.getId()).getName());
      }

// delete test

      @Test
      public void delete_deletesStylists_true() {
        Stylists myStylists = new Stylists("Mow");
        myStylists.save();
        int myStylistsId = myStylists.getId();
        myStylists.delete();
        assertEquals(null, Stylists.find(myStylistsId));
      }

}
