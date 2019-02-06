import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CategoryTest {

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
  public void category_instantiatesCorrectly_true() {
    Category testCategory = new Category("Home");
    assertEquals(true, testCategory instanceof Category);
  }

      @Test
  public void getName_categoryInstantiatesWithName_Home() {
    Category testCategory = new Category("Home");
    assertEquals("Home", testCategory.getName());
  }

// update one of our existing tests to use our new .save() method:
      @Test
      public void all_returnsAllInstancesOfCategory_true() {
        Category firstCategory = new Category("Home");
        firstCategory.save();
        Category secondCategory = new Category("Work");
        secondCategory.save();
        assertEquals(true, Category.all().get(0).equals(firstCategory));
        assertEquals(true, Category.all().get(1).equals(secondCategory));
      }

// We'll also modify our existing test for the getId() method:
     @Test
     public void getId_categoriesInstantiateWithAnId_1() {
       Category testCategory = new Category("Home");
       testCategory.save();
       assertTrue(testCategory.getId() > 0);
     }

  //   @Test
  // public void find_returnsCategoryWithSameId_secondCategory() {
  //   Category firstCategory = new Category("Home");
  //   Category secondCategory = new Category("Work");
  //   assertEquals(Category.find(secondCategory.getId()), secondCategory);
  // }

	@Test
	public void getTasks_initiallyReturnsEmptyList_ArrayList() {
	  Category testCategory = new Category("Home");
	  assertEquals(0, testCategory.getTasks().size());
  }

// find objects saved in the database.
     @Test
     public void find_returnsCategoryWithSameId_secondCategory() {
       Category firstCategory = new Category("Home");
       firstCategory.save();
       Category secondCategory = new Category("Work");
       secondCategory.save();
       assertEquals(Category.find(secondCategory.getId()), secondCategory);
     }

// Overriding equals()
      @Test
      public void equals_returnsTrueIfNamesAretheSame() {
        Category firstCategory = new Category("Household chores");
        Category secondCategory = new Category("Household chores");
        assertTrue(firstCategory.equals(secondCategory));
      }

// Saving New Objects to the Database test
      @Test
      public void save_savesIntoDatabase_true() {
        Category myCategory = new Category("Household chores");
        myCategory.save();
        assertTrue(Category.all().get(0).equals(myCategory));
      }

// Assigning Unique IDs
      @Test
      public void save_assignsIdToObject() {
        Category myCategory = new Category("Household chores");
        myCategory.save();
        Category savedCategory = Category.all().get(0);
        assertEquals(myCategory.getId(), savedCategory.getId());
      }

// to retrieve all Tasks saved into a specific Category.import java.util.Arrays;
      @Test
      public void getTasks_retrievesALlTasksFromDatabase_tasksList() {
        Category myCategory = new Category("Household chores");
        myCategory.save();
        Task firstTask = new Task("Mow the lawn", myCategory.getId());
        firstTask.save();
        Task secondTask = new Task("Do the dishes", myCategory.getId());
        secondTask.save();
        Task[] tasks = new Task[] { firstTask, secondTask };
        assertTrue(myCategory.getTasks().containsAll(Arrays.asList(tasks)));
      }

}
