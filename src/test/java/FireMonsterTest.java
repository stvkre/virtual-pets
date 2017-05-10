import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.Date;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class FireFireMonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void fireFireMonster_instantiatesCorrectly_true() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    assertEquals(true, testFireMonster instanceof FireMonster);
  }

// instatiate FireMonster class with a name
  @Test
 public void FireMonster_instantiatesWithName_String() {
   FireMonster testFireMonster = new FireMonster("Bubbles", 1);
   assertEquals("Bubbles", testFireMonster.getName());
 }

 // instantiate FireMonster class with person ID

 @Test
  public void FireMonster_instantiatesWithPersonId_int() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    assertEquals(1, testFireMonster.getPersonId());
  }

  // Overriding equals function
  @Test
  public void equals_returnsTrueIfNameAndPersonIdAreSame_true() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    FireMonster anotherFireMonster = new FireMonster("Bubbles", 1);
    assertTrue(testFireMonster.equals(anotherFireMonster));
  }

  // saving FireMonsters class to the database
  @Test
 public void save_returnsTrueIfDescriptionsAretheSame() {
   FireMonster testFireMonster = new FireMonster("Bubbles", 1);
   testFireMonster.save();
   assertTrue(FireMonster.all().get(0).equals(testFireMonster));
 }

 // setting FireMonster's Id attribute
 @Test
  public void save_assignsIdToFireMonster() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.save();
    FireMonster savedFireMonster = FireMonster.all().get(0);
    assertEquals(savedFireMonster.getId(), testFireMonster.getId());
  }

  // returning all instances of FireMonster method
  @Test
 public void all_returnsAllInstancesOfFireMonster_true() {
   FireMonster firstFireMonster = new FireMonster("Bubbles", 1);
   firstFireMonster.save();
   FireMonster secondFireMonster = new FireMonster("Spud", 1);
   secondFireMonster.save();
   assertEquals(true, FireMonster.all().get(0).equals(firstFireMonster));
   assertEquals(true, FireMonster.all().get(1).equals(secondFireMonster));
 }

 // associating one Person to many fireFireMonsters
 @Test
  public void save_savesPersonIdIntoDB_true() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    FireMonster testFireMonster = new FireMonster("Bubbles", testPerson.getId());
    testFireMonster.save();
    FireMonster savedFireMonster = FireMonster.find(testFireMonster.getId());
    assertEquals(savedFireMonster.getPersonId(), testPerson.getId());
  }

  // instantiating the first constant: playLevel
  @Test
  public void fireFireMonster_instantiatesWithHalfFullPlayLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    assertEquals(testFireMonster.getPlayLevel(), (FireMonster.MAX_PLAY_LEVEL / 2));
  }

  // instatiating the second constant: sleepLevel
  @Test
  public void fireFireMonster_instantiatesWithHalfFullSleepLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    assertEquals(testFireMonster.getSleepLevel(), (FireMonster.MAX_SLEEP_LEVEL / 2));
  }

  // instatiating the third constant: foodLevel
  @Test
  public void fireFireMonster_instantiatesWithHalfFullFoodLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    assertEquals(testFireMonster.getFoodLevel(), (FireMonster.MAX_FOOD_LEVEL / 2));
  }

  // checking a fireFireMonster's minimum levels
  @Test
  public void isAlive_confirmsFireMonsterIsAliveIfAllLevelsAboveMinimum_true(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    assertEquals(testFireMonster.isAlive(), true);
  }

  // checking a fireFireMonster's depleted levels
  @Test
  public void depleteLevels_reducesAllLevels(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.depleteLevels();
    assertEquals(testFireMonster.getFoodLevel(), (FireMonster.MAX_FOOD_LEVEL / 2) - 1);
    assertEquals(testFireMonster.getSleepLevel(), (FireMonster.MAX_SLEEP_LEVEL / 2) - 1);
    assertEquals(testFireMonster.getPlayLevel(), (FireMonster.MAX_PLAY_LEVEL / 2) - 1);
  }

  @Test
  public void isAlive_recognizesFireMonsterIsDeadWhenLevelsReachMinimum_false(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= FireMonster.MAX_FOOD_LEVEL; i++){
      testFireMonster.depleteLevels();
    }
    assertEquals(testFireMonster.isAlive(), false);
  }

  // increasing playLevel
  @Test
  public void play_increasesFireMonsterPlayLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.play();
    assertTrue(testFireMonster.getPlayLevel() > (FireMonster.MAX_PLAY_LEVEL / 2));
  }

// increasing sleepLevel
@Test
  public void sleep_increasesFireMonsterSleepLevel(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.sleep();
    assertTrue(testFireMonster.getSleepLevel() > (FireMonster.MAX_SLEEP_LEVEL / 2));
  }

  // increase foodLevel
  @Test
 public void feed_increasesFireMonsterFoodLevel(){
   FireMonster testFireMonster = new FireMonster("Bubbles", 1);
   testFireMonster.feed();
   assertTrue(testFireMonster.getFoodLevel() > (FireMonster.MAX_FOOD_LEVEL / 2));
 }

 // throwing an exception to ensure food levels dont surpass the maximum VALUES
 @Test(expected = UnsupportedOperationException.class)
 public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
   FireMonster testFireMonster = new FireMonster("Bubbles", 1);
   for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_FOOD_LEVEL); i++){
     testFireMonster.feed();
   }
 }

 // check maximum food levels
 @Test
 public void fireFireMonster_foodLevelCannotGoBeyondMaxValue(){
   FireMonster testFireMonster = new FireMonster("Bubbles", 1);
   // catching the exception of food levels attempting to surpass the maximum VALUES
   for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_FOOD_LEVEL); i++){
     try {
       testFireMonster.feed();
     } catch (UnsupportedOperationException exception){ }
   }
   assertTrue(testFireMonster.getFoodLevel() <= FireMonster.MAX_FOOD_LEVEL);
 }


 // throwing an exception to ensure play levels dont surpass the maximum VALUES
 @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_PLAY_LEVEL); i++){
      testFireMonster.play();
    }
  }

  @Test
    public void fireFireMonster_playLevelCannotGoBeyondMaxValue(){
      FireMonster testFireMonster = new FireMonster("Bubbles", 1);
      // catching the exception of play levels attempting to surpass the maximum VALUES

      for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_PLAY_LEVEL); i++){
        try {
          testFireMonster.play();
        } catch (UnsupportedOperationException exception){ }
      }
      assertTrue(testFireMonster.getPlayLevel() <= FireMonster.MAX_PLAY_LEVEL);
    }

  // throwing an exception to ensure sleep levels dont surpass the maximum VALUES
@Test(expected = UnsupportedOperationException.class)
   public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
     FireMonster testFireMonster = new FireMonster("Bubbles", 1);
     for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_SLEEP_LEVEL); i++){
       testFireMonster.sleep();
     }
   }

   @Test
  public void fireFireMonster_sleepLevelCannotGoBeyondMaxValue(){
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);

    // catching the exception of sleep levels attempting to surpass the maximum VALUES

    for(int i = FireMonster.MIN_ALL_LEVELS; i <= (FireMonster.MAX_SLEEP_LEVEL); i++){
      try {
        testFireMonster.sleep();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testFireMonster.getSleepLevel() <= FireMonster.MAX_SLEEP_LEVEL);
  }

  // adding a fireFireMonster's birthday
  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.save();
    Timestamp savedFireMonsterBirthday = FireMonster.find(testFireMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterBirthday));
  }

  // asserting sleep method to update the lastslept value accurately
  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.save();
    testFireMonster.sleep();
    Timestamp savedFireMonsterLastSlept = FireMonster.find(testFireMonster.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastSlept));
  }

  // asserting ate method to update the lastAte value accurately

  @Test
    public void feed_recordsTimeLastAteInDatabase() {
      FireMonster testFireMonster = new FireMonster("Bubbles", 1);
      testFireMonster.save();
      testFireMonster.feed();
      Timestamp savedFireMonsterLastAte = FireMonster.find(testFireMonster.getId()).getLastAte();
      Timestamp rightNow = new Timestamp(new Date().getTime());
      assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastAte));
    }

    // asserting play method to update the lastPlayed value accurately
    @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    testFireMonster.save();
    testFireMonster.play();
    Timestamp savedFireMonsterLastPlayed = FireMonster.find(testFireMonster.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedFireMonsterLastPlayed));
  }

  // insert timer
  @Test
  public void timer_executesDepleteLevelsMethod() {
    FireMonster testFireMonster = new FireMonster("Bubbles", 1);
    int firstPlayLevel = testFireMonster.getPlayLevel();
    testFireMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    int secondPlayLevel = testFireMonster.getPlayLevel();
    assertTrue(firstPlayLevel > secondPlayLevel);
  }

  // fireFireMonster levels reaching 0
  @Test
 public void timer_haltsAfterFireMonsterDies() {
   FireMonster testFireMonster = new FireMonster("Bubbles", 1);
   testFireMonster.startTimer();
   try {
     Thread.sleep(6000);
   } catch (InterruptedException exception){}
   assertFalse(testFireMonster.isAlive());
   assertTrue(testFireMonster.getFoodLevel() >= 0);
 }
}
