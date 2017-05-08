import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class MonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void monster_instantiatesCorrectly_true() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(true, testMonster instanceof Monster);
  }

// instatiate Monster class with a name
  @Test
 public void Monster_instantiatesWithName_String() {
   Monster testMonster = new Monster("Bubbles", 1);
   assertEquals("Bubbles", testMonster.getName());
 }

 // instantiate Monster class with person ID

 @Test
  public void Monster_instantiatesWithPersonId_int() {
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(1, testMonster.getPersonId());
  }

  // Overriding equals function
  @Test
  public void equals_returnsTrueIfNameAndPersonIdAreSame_true() {
    Monster testMonster = new Monster("Bubbles", 1);
    Monster anotherMonster = new Monster("Bubbles", 1);
    assertTrue(testMonster.equals(anotherMonster));
  }

  // saving Monsters class to the database
  @Test
 public void save_returnsTrueIfDescriptionsAretheSame() {
   Monster testMonster = new Monster("Bubbles", 1);
   testMonster.save();
   assertTrue(Monster.all().get(0).equals(testMonster));
 }

 // setting Monster's Id attribute
 @Test
  public void save_assignsIdToMonster() {
    Monster testMonster = new Monster("Bubbles", 1);
    testMonster.save();
    Monster savedMonster = Monster.all().get(0);
    assertEquals(savedMonster.getId(), testMonster.getId());
  }

  // returning all instances of Monster method
  @Test
 public void all_returnsAllInstancesOfMonster_true() {
   Monster firstMonster = new Monster("Bubbles", 1);
   firstMonster.save();
   Monster secondMonster = new Monster("Spud", 1);
   secondMonster.save();
   assertEquals(true, Monster.all().get(0).equals(firstMonster));
   assertEquals(true, Monster.all().get(1).equals(secondMonster));
 }

 // associating one Person to many monsters
 @Test
  public void save_savesPersonIdIntoDB_true() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    Monster testMonster = new Monster("Bubbles", testPerson.getId());
    testMonster.save();
    Monster savedMonster = Monster.find(testMonster.getId());
    assertEquals(savedMonster.getPersonId(), testPerson.getId());
  }

  // instantiating the first constant: playLevel
  @Test
  public void monster_instantiatesWithHalfFullPlayLevel(){
    Monster testMonster = new Monster("Bubbles", 1);
    assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2));
  }
}
