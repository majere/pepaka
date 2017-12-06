package sample;


public class Donate {
    static void pay(long user_id){
        Hero hero = Postgre.getHero(user_id);

    }
}
