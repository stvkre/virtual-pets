import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;


public class Monster {
  private String name;
  private int id;
  private int personId;
  private int foodLevel;
  private int sleepLevel;
  private int playLevel;
  private Timestamp birthday;
  private Timestamp lastAte;
  private Timestamp lastSlept;
  private Timestamp lastPlayed;

  public static final int MAX_FOOD_LEVEL = 3;
  public static final int MAX_SLEEP_LEVEL = 8;
  public static final int MAX_PLAY_LEVEL = 12;
  public static final int MIN_ALL_LEVELS = 0;

  public Monster(String name, int personId) {

    this.name = name;
    this.personId = personId;
    playLevel = MAX_PLAY_LEVEL / 2;
    sleepLevel = MAX_SLEEP_LEVEL / 2;
    foodLevel = MAX_FOOD_LEVEL / 2;
  }



 public boolean isAlive() {
    if (foodLevel <= MIN_ALL_LEVELS ||
    playLevel <= MIN_ALL_LEVELS ||
    sleepLevel <= MIN_ALL_LEVELS) {
      return false;
    }
    return true;
  }


  public String getName() {
    return name;
  }

  public int getPersonId() {
    return personId;
  }

  public int getId() {
    return id;
  }

  public int getPlayLevel() {
    return playLevel;
  }

  public int getSleepLevel(){
    return sleepLevel;
  }

  public int getFoodLevel() {
    return foodLevel;
  }

  public Timestamp getBirthday(){
    return birthday;
  }

  public Timestamp getLastSlept(){
    return lastSlept;
  }

  @Override
  public boolean equals(Object otherMonster){
    if (!(otherMonster instanceof Monster)) {
      return false;
    } else {
      Monster newMonster = (Monster) otherMonster;
      return this.getName().equals(newMonster.getName()) &&
             this.getPersonId() == newMonster.getPersonId();
    }
  }

  // save method to include the birthday of every new monster
  public void save() {

   try(Connection con = DB.sql2o.open()) {
     String sql = "INSERT INTO monsters (name, personId, birthday) VALUES (:name, :personId, now())";
     this.id = (int) con.createQuery(sql, true)
       .addParameter("name", this.name)
       .addParameter("personId", this.personId)
       .executeUpdate()
       .getKey();
   }
 }


  public static List<Monster> all() {
   String sql = "SELECT * FROM monsters";
   try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Monster.class);
   }
 }

 public static Monster find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM monsters where id=:id";
      Monster monster = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Monster.class);
      return monster;
    }
  }

  public void depleteLevels(){
      playLevel--;
      foodLevel--;
      sleepLevel--;
    }

  public void play() {

    // throwing an exception if user tries to raise the pet's playLevel above maximum value

    if (playLevel >= MAX_PLAY_LEVEL){
     throw new UnsupportedOperationException("You cannot play with monster anymore!");
   }
    playLevel++;
  }

  public void sleep(){

    // throwing an exception if user tries to raise the pet's sleepLevel above maximum value

    if (sleepLevel >= MAX_SLEEP_LEVEL){
     throw new UnsupportedOperationException("You cannot make your monster sleep anymore!");
   }
   // inserting a new timestamp in the colomn
   try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE monsters SET lastslept = now() WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      }
    sleepLevel++;
  }

  public void feed(){

    // throwing an exception if user tries to raise the pet's foodLevel above maximum value

    if (foodLevel >= MAX_FOOD_LEVEL){
      throw new UnsupportedOperationException("You cannot feed your monster anymore!");
    }
    foodLevel++;

}

}
