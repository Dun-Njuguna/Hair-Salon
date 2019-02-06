import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import org.sql2o.*;

public class TaskTest {

// creates a new connection to the database before every test
      @Before
      public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/salon", "njuguna", "pass");
      }

// clears the database after every test
      @After
      public void tearDown() {
        try(Connection con = DB.sql2o.open()) {
          String deleteTasksQuery = "DELETE FROM tasks *;";
          String deleteCategoriesQuery = "DELETE FROM categories *;";
          con.createQuery(deleteTasksQuery).executeUpdate();
          con.createQuery(deleteCategoriesQuery).executeUpdate();
        }
      }

    @Test
    public void Task_instantiatesCorrectly_true() {
        Task myTask = new Task("Mow the lawn", 1);
        assertEquals(true, myTask instanceof Task);
    }

    @Test
    public void Task_instantiatesWithDescription_String() {
        Task myTask = new Task("Mow the lawn", 1);
        assertEquals("Mow the lawn", myTask.getDescription());
    }

    @Test
    public void isCompleted_isFalseAfterInstantiation_false() {
        Task myTask = new Task("Mow the lawn", 1);
        assertEquals(false, myTask.isCompleted());
    }

    @Test
    public void getCreatedAt_instantiatesWithCurrentTime_today() {
        Task myTask = new Task("Mow the lawn", 1);
        assertEquals(LocalDateTime.now().getDayOfWeek(), myTask.getCreatedAt().getDayOfWeek());
    }

// returns all data in database while still saving using sava()
      @Test
      public void all_returnsAllInstancesOfTask_true() {
        Task firstTask = new Task("Mow the lawn", 1);
        firstTask.save();
        Task secondTask = new Task("Mow the lawn", 1);
        secondTask.save();
        assertEquals(true, Task.all().get(0).equals(firstTask));
        assertEquals(true, Task.all().get(1).equals(secondTask));
      }

    @Test
    public void clear_emptiesAllTasksFromArrayList_0() {
        Task myTask = new Task("Mow the lawn", 1);
        assertEquals(Task.all().size(), 0);
    }

// this test checks that the id is greater than 0 from DB
    @Test
    public void getId_tasksInstantiateWithAnID() {
      Task myTask = new Task("Mow the lawn", 1);
      myTask.save();
      assertTrue(myTask.getId() > 0);
    }
// Our Task.find(myTask.getId()) uses the Task's id to query the database and return a Task object.
      @Test
      public void find_returnsTaskWithSameId_secondTask() {
        Task firstTask = new Task("Mow the lawn", 1);
        firstTask.save();
        Task secondTask = new Task("Mow the lawn", 1);
        secondTask.save();
        assertEquals(Task.find(secondTask.getId()), secondTask);
      }

// a test to check whether to instances of info retriieved from the database are the same 
    @Test
    public void equals_returnsTrueIfDescriptionsAretheSame() {
      Task firstTask = new Task("Mow the lawn", 1);
      Task secondTask = new Task("Mow the lawn", 1);
      assertTrue(firstTask.equals(secondTask));
    }

// saving objects into the database
    @Test
    public void save_returnsTrueIfDescriptionsAretheSame() {
      Task myTask = new Task("Mow the lawn", 1);
      myTask.save();
      assertTrue(Task.all().get(0).equals(myTask));
    }

// test ids of tasks in database and application
    @Test
    public void save_assignsIdToObject() {
      Task myTask = new Task("Mow the lawn", 1);
      myTask.save();
      Task savedTask = Task.all().get(0);
      assertEquals(myTask.getId(), savedTask.getId());
    }

// Next, we'll write a unit test that ensures 
//we can save a categoryId into our tasks table, 
//thereby associating a Task with its Category:
      @Test
      public void save_savesCategoryIdIntoDB_true() {
        Category myCategory = new Category("Household chores");
        myCategory.save();
        Task myTask = new Task("Mow the lawn", myCategory.getId());
        myTask.save();
        Task savedTask = Task.find(myTask.getId());
        assertEquals(savedTask.getCategoryId(), myCategory.getId());
      }

}
