import java.text.DateFormat;
import java.sql.Timestamp;
import java.util.Date;
import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class WaterMonsterTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void WaterMonster_instantiatesCorrectly_true() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(true, testWaterMonster instanceof WaterMonster);
  }

// instatiate WaterMonster class with a name
  @Test
 public void WaterMonster_instantiatesWithName_String() {
   WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
   assertEquals("Bubbles", testWaterMonster.getName());
 }

 // instantiate WaterMonster class with person ID

 @Test
  public void WaterMonster_instantiatesWithPersonId_int() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(1, testWaterMonster.getPersonId());
  }

  // Overriding equals function
  @Test
  public void equals_returnsTrueIfNameAndPersonIdAreSame_true() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    WaterMonster anotherWaterMonster = new WaterMonster("Bubbles", 1);
    assertTrue(testWaterMonster.equals(anotherWaterMonster));
  }

  // saving WaterMonsters class to the database
  @Test
 public void save_returnsTrueIfDescriptionsAretheSame() {
   WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
   testWaterMonster.save();
   assertTrue(WaterMonster.all().get(0).equals(testWaterMonster));
 }

 // setting WaterMonster's Id attribute
 @Test
  public void save_assignsIdToWaterMonster() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    WaterMonster savedWaterMonster = WaterMonster.all().get(0);
    assertEquals(savedWaterMonster.getId(), testWaterMonster.getId());
  }

  // returning all instances of WaterMonster method
  @Test
 public void all_returnsAllInstancesOfWaterMonster_true() {
   WaterMonster firstWaterMonster = new WaterMonster("Bubbles", 1);
   firstWaterMonster.save();
   WaterMonster secondWaterMonster = new WaterMonster("Spud", 1);
   secondWaterMonster.save();
   assertEquals(true, WaterMonster.all().get(0).equals(firstWaterMonster));
   assertEquals(true, WaterMonster.all().get(1).equals(secondWaterMonster));
 }

 // associating one Person to many WaterMonsters
 @Test
  public void save_savesPersonIdIntoDB_true() {
    Person testPerson = new Person("Henry", "henry@henry.com");
    testPerson.save();
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", testPerson.getId());
    testWaterMonster.save();
    WaterMonster savedWaterMonster = WaterMonster.find(testWaterMonster.getId());
    assertEquals(savedWaterMonster.getPersonId(), testPerson.getId());
  }

  // instantiating the first constant: playLevel
  @Test
  public void WaterMonster_instantiatesWithHalfFullPlayLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.getPlayLevel(), (WaterMonster.MAX_PLAY_LEVEL / 2));
  }

  // instatiating the second constant: sleepLevel
  @Test
  public void WaterMonster_instantiatesWithHalfFullSleepLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.getSleepLevel(), (WaterMonster.MAX_SLEEP_LEVEL / 2));
  }

  // instatiating the third constant: foodLevel
  @Test
  public void WaterMonster_instantiatesWithHalfFullFoodLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.getFoodLevel(), (WaterMonster.MAX_FOOD_LEVEL / 2));
  }

  // checking a WaterMonster's minimum levels
  @Test
  public void isAlive_confirmsWaterMonsterIsAliveIfAllLevelsAboveMinimum_true(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    assertEquals(testWaterMonster.isAlive(), true);
  }

  // checking a WaterMonster's depleted levels
  @Test
  public void depleteLevels_reducesAllLevels(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.depleteLevels();
    assertEquals(testWaterMonster.getFoodLevel(), (WaterMonster.MAX_FOOD_LEVEL / 2) - 1);
    assertEquals(testWaterMonster.getSleepLevel(), (WaterMonster.MAX_SLEEP_LEVEL / 2) - 1);
    assertEquals(testWaterMonster.getPlayLevel(), (WaterMonster.MAX_PLAY_LEVEL / 2) - 1);
  }

  @Test
  public void isAlive_recognizesWaterMonsterIsDeadWhenLevelsReachMinimum_false(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= WaterMonster.MAX_FOOD_LEVEL; i++){
      testWaterMonster.depleteLevels();
    }
    assertEquals(testWaterMonster.isAlive(), false);
  }

  // increasing playLevel
  @Test
  public void play_increasesWaterMonsterPlayLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.play();
    assertTrue(testWaterMonster.getPlayLevel() > (WaterMonster.MAX_PLAY_LEVEL / 2));
  }

// increasing sleepLevel
@Test
  public void sleep_increasesWaterMonsterSleepLevel(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.sleep();
    assertTrue(testWaterMonster.getSleepLevel() > (WaterMonster.MAX_SLEEP_LEVEL / 2));
  }

  // increase foodLevel
  @Test
 public void feed_increasesWaterMonsterFoodLevel(){
   WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
   testWaterMonster.feed();
   assertTrue(testWaterMonster.getFoodLevel() > (WaterMonster.MAX_FOOD_LEVEL / 2));
 }

 // throwing an exception to ensure food levels dont surpass the maximum VALUES
 @Test(expected = UnsupportedOperationException.class)
 public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
   WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
   for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_FOOD_LEVEL); i++){
     testWaterMonster.feed();
   }
 }

 // check maximum food levels
 @Test
 public void WaterMonster_foodLevelCannotGoBeyondMaxValue(){
   WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
   // catching the exception of food levels attempting to surpass the maximum VALUES
   for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_FOOD_LEVEL); i++){
     try {
       testWaterMonster.feed();
     } catch (UnsupportedOperationException exception){ }
   }
   assertTrue(testWaterMonster.getFoodLevel() <= WaterMonster.MAX_FOOD_LEVEL);
 }


 // throwing an exception to ensure play levels dont surpass the maximum VALUES
 @Test(expected = UnsupportedOperationException.class)
  public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_PLAY_LEVEL); i++){
      testWaterMonster.play();
    }
  }

  @Test
    public void WaterMonster_playLevelCannotGoBeyondMaxValue(){
      WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
      // catching the exception of play levels attempting to surpass the maximum VALUES

      for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_PLAY_LEVEL); i++){
        try {
          testWaterMonster.play();
        } catch (UnsupportedOperationException exception){ }
      }
      assertTrue(testWaterMonster.getPlayLevel() <= WaterMonster.MAX_PLAY_LEVEL);
    }

  // throwing an exception to ensure sleep levels dont surpass the maximum VALUES
@Test(expected = UnsupportedOperationException.class)
   public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
     WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
     for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_SLEEP_LEVEL); i++){
       testWaterMonster.sleep();
     }
   }

   @Test
  public void WaterMonster_sleepLevelCannotGoBeyondMaxValue(){
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);

    // catching the exception of sleep levels attempting to surpass the maximum VALUES

    for(int i = WaterMonster.MIN_ALL_LEVELS; i <= (WaterMonster.MAX_SLEEP_LEVEL); i++){
      try {
        testWaterMonster.sleep();
      } catch (UnsupportedOperationException exception){ }
    }
    assertTrue(testWaterMonster.getSleepLevel() <= WaterMonster.MAX_SLEEP_LEVEL);
  }

  // adding a WaterMonster's birthday
  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    Timestamp savedWaterMonsterBirthday = WaterMonster.find(testWaterMonster.getId()).getBirthday();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterBirthday));
  }

  // asserting sleep method to update the lastslept value accurately
  @Test
  public void sleep_recordsTimeLastSleptInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    testWaterMonster.sleep();
    Timestamp savedWaterMonsterLastSlept = WaterMonster.find(testWaterMonster.getId()).getLastSlept();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastSlept));
  }

  // asserting ate method to update the lastAte value accurately

  @Test
    public void feed_recordsTimeLastAteInDatabase() {
      WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
      testWaterMonster.save();
      testWaterMonster.feed();
      Timestamp savedWaterMonsterLastAte = WaterMonster.find(testWaterMonster.getId()).getLastAte();
      Timestamp rightNow = new Timestamp(new Date().getTime());
      assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastAte));
    }

    // asserting play method to update the lastPlayed value accurately
    @Test
  public void play_recordsTimeLastPlayedInDatabase() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    testWaterMonster.save();
    testWaterMonster.play();
    Timestamp savedWaterMonsterLastPlayed = WaterMonster.find(testWaterMonster.getId()).getLastPlayed();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedWaterMonsterLastPlayed));
  }

  // insert timer
  @Test
  public void timer_executesDepleteLevelsMethod() {
    WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
    int firstPlayLevel = testWaterMonster.getPlayLevel();
    testWaterMonster.startTimer();
    try {
      Thread.sleep(6000);
    } catch (InterruptedException exception){}
    int secondPlayLevel = testWaterMonster.getPlayLevel();
    assertTrue(firstPlayLevel > secondPlayLevel);
  }

  // WaterMonster levels reaching 0
  @Test
 public void timer_haltsAfterWaterMonsterDies() {
   WaterMonster testWaterMonster = new WaterMonster("Bubbles", 1);
   testWaterMonster.startTimer();
   try {
     Thread.sleep(6000);
   } catch (InterruptedException exception){}
   assertFalse(testWaterMonster.isAlive());
   assertTrue(testWaterMonster.getFoodLevel() >= 0);
 }
}
