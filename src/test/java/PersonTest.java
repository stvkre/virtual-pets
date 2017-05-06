import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;


public class PersonTest {

  @Test
  public void person_instantiatesCorrectly_true() {
    Person testPerson = new Person("Henry", "[email protected]");
    assertEquals(true, testPerson instanceof Person);
  }

  @Test
  public void getName_personInstantiatesWithName_Henry() {
    Person testPerson = new Person("Henry", "[email protected]");
    assertEquals("Henry", testPerson.getName());
  }

  @Test
  public void getEmail_personInstantiatesWithEmail_String() {
    Person testPerson = new Person("Henry", "[email protected]");
    assertEquals("[email protected]"), testPerson.getEmail();
  }

  @Test
  public void equals_returnsTrueIfNameAndEmailAreSame() {
    Person firstPerson = new Person("Henry", "[email protected]");
    Person anotherPerson = new Person("Henry", "[email protected]");
    assertTrue(firstPerson.equals(anotherPerson));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Person() {
    Person testPerson = new Person("Henry", "[email protected]");
    testPerson.save();
    assertTrue(Person.all().get(0).equals(testPerson));
  }

// database rule
  public class PersonTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();
  }

//all method
  @Test
  public void all_returnsAllInstancesOfPerson_true() {
    Person firstPerson = new Person("Henry", "henry@henry.com");
    firstPerson.save();
    Person secondPerson = new Person("Harriet", "harriet@harriet.com");
    secondPerson.save();
    assertEquals(true, Person.all().get(0).equals(firstPerson));
    assertEquals(true, Person.all().get(1).equals(secndPerson));
  }
}
