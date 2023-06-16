package tutorial;

public class Wizard extends Dude {

    ArrayList<Spell> spells;
    
    public class cast(String spell) {
        System.out.println("Boo");
        mp -= 10;
    }
}


//In other file

public class GrandWizard extends Wizard {
    public void sayName() {
        System.out.println("Grand Wizard, " + name);
        
    }
}

grandWizard1.name = "Flash";
grandWizard1.sayName();
((Dude)grandWizard1).sayName();
