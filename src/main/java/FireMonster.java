import java.util.Timer;
import org.sql2o.*;

public class FireMonster extends Monster {

  public FireMonster(String name, int personId) {
    this.name = name;
    this.personId = personId;
    playLevel = MAX_PLAY_LEVEL / 2;
    sleepLevel = MAX_SLEEP_LEVEL / 2;
    foodLevel = MAX_FOOD_LEVEL / 2;
    timer = new Timer();
  }

}
