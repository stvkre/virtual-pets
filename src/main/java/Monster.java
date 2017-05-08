import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;


public class Monster {
  private String name;
  private int id;

  public Monster(String name, int personId) {
    this.name = name;
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

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO monsters (name, personid) VALUES (:name, :personId)";
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
}
