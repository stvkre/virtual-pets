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
}
