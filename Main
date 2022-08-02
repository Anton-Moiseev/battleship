package battleship;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Field battleField1 = new Field("Player 1");

        System.out.println("Player 1, place your ships on the game field");

        battleField1.putAllShips();

        pressEnterKey();

        Field battleField2 = new Field("Player 2");

        battleField1.setEnemyField(battleField2.getField());
        battleField1.setFoggedEnemyField(battleField2.getFoggedField());
        battleField1.setEnemyFieldObj(battleField2);
        battleField2.setEnemyField(battleField1.getField());
        battleField2.setFoggedEnemyField(battleField1.getFoggedField());
        battleField2.setEnemyFieldObj(battleField1);

        battleField2.putAllShips();

        pressEnterKey();

        BattleProcess battleProcess = new BattleProcess(battleField1, battleField2);

        battleProcess.start();

    }

    public static void pressEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
