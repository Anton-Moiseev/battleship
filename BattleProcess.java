package battleship;

public class BattleProcess {

    private final Field battleField1;

    private final Field battleField2;

    public BattleProcess(Field battleField1, Field battleField2) {
        this.battleField1 = battleField1;
        this.battleField2 = battleField2;
    }

    public Field getBattleField1() {
        return battleField1;
    }

    public Field getBattleField2() {
        return battleField2;
    }

    public void start() {

        boolean bothShot = false;

        while (!battleField1.gameIsOver &&
                !battleField2.gameIsOver) {
            battleField1.shoot();
            bothShot = false;
            while (!battleField1.gameIsOver &&
                    !battleField2.gameIsOver && !bothShot) {
                battleField2.shoot();
                bothShot = true;
            }
        }

    }

}
