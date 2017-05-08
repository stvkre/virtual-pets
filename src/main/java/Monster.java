public class Monster {
  private String name;

  public Monster(String name, int personId) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getPersonId() {
    return personId;
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
}
