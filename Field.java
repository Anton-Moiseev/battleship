package battleship;
import java.util.Scanner;

public class Field {

    String playerName;

    private final char[][] field = new char[10][10];

    private final char[][] foggedField = new char[10][10];

    private char[][] enemyField;

    private char[][] foggedEnemyField;

    private Field enemyFieldObj;

    private final Ship aircraftCarrier = new Ship(5);
    private final Ship battleship = new Ship(4);
    private final Ship submarine = new Ship(3);
    private final Ship cruiser = new Ship(3);
    private final Ship destroyer = new Ship(2);

    private int counter = 0;

    public boolean gameIsOver = false;

    public Field(String name) {
        this.playerName = name;
        createCleanField();
    }

    Scanner scanner = new Scanner(System.in);

    private void createCleanField() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                field[i][j] = '~';
                foggedField[i][j] = '~';
            }
        }
    }

    public char[][] getField() {
        return field;
    }

    public char[][] getFoggedField() {
        return foggedField;
    }

    public void setEnemyField(char[][] field) {
        this.enemyField = field;
    }

    public Field getEnemyFieldObj() {
        return enemyFieldObj;
    }

    public void setEnemyFieldObj(Field field) {
        this.enemyFieldObj = field;
    }

    public void setFoggedEnemyField(char[][] field) {
        this.foggedEnemyField = field;
    }

    public void printField(char[][] field) {
        System.out.print(" ");
        for (int i = 1; i <= 10; ++i) {
            System.out.print(" " + i);
        }
        System.out.print("\n");
        for (char i = 0; i < 10; ++i) {
            System.out.print((char)('A' + i));
            for (int j = 0; j < 10; ++j) {
                System.out.print(" " + field[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    private boolean putShip(int size, String name) {
        String coordsLine = scanner.nextLine();
        String[] coordsArr = coordsLine.split(" ");
        String startCoords = coordsArr[0];
        String endCoords = coordsArr[1];

        char startCoordsRow = startCoords.charAt(0);
        int startCoordsColumn = Integer.parseInt(startCoords.substring(1, startCoords.length()));
        char endCoordsRow = endCoords.charAt(0);
        int endCoordsColumn = Integer.parseInt(endCoords.substring(1, endCoords.length()));

        if (startCoordsRow > endCoordsRow) {
            char tempStart = startCoordsRow;
            startCoordsRow = endCoordsRow;
            endCoordsRow = tempStart;
        }
        if (startCoordsColumn > endCoordsColumn) {
            int tempStart = startCoordsColumn;
            startCoordsColumn = endCoordsColumn;
            endCoordsColumn = tempStart;
        }

        try {
            if ((endCoordsColumn - startCoordsColumn + 1 != size) &&
                    endCoordsRow - startCoordsRow + 1 != size) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.printf("Error! Wrong length of the %s! Try again:\n", name);
            return false;
        }

        try {
            if (startCoordsRow != endCoordsRow && startCoordsColumn != endCoordsColumn) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }

        try {
            if (startCoordsRow < 'A' || startCoordsRow > 'J'
                    || startCoordsColumn < 1 || startCoordsColumn > 10) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }

        if (!checkNeighbouringShips(startCoordsRow, startCoordsColumn,
                endCoordsRow, endCoordsColumn)) {
            return false;
        }

        putShipInArr(startCoordsRow, startCoordsColumn,
                endCoordsRow, endCoordsColumn);

        switch(name) {
            case "Aircraft Carrier":
                aircraftCarrier.fillShipCells(startCoords, endCoords);
                break;
            case "Battleship":
                battleship.fillShipCells(startCoords, endCoords);
                break;
            case "Cruiser":
                cruiser.fillShipCells(startCoords, endCoords);
                break;
            case "Submarine":
                submarine.fillShipCells(startCoords, endCoords);
                break;
            case "Destroyer":
                destroyer.fillShipCells(startCoords, endCoords);
                break;
        }

        printField(field);

        return true;

    }

    private boolean checkNeighbouringShips(char startCoordsRow, int startCoordsColumn,
                                           char endCoordsRow, int endCoordsColumn) {
        int arrIndexStartRow = (int)(startCoordsRow - 65);
        int arrIndexEndRow = (int)(endCoordsRow - 65);
        int arrIndexStartColumn = startCoordsColumn - 1;
        int arrIndexEndColumn = endCoordsColumn - 1;
        if (startCoordsRow == endCoordsRow) {
            for (int i = arrIndexStartColumn; i <= arrIndexEndColumn; ++i) {
                for (int j = arrIndexStartRow - 1; j <= arrIndexStartRow + 1; ++j) {
                    if (j < 0 || j > 9) {
                        break;
                    }
                    for (int z = i - 1; z <= i + 1; ++z) {
                        if (z < 0 || z > 9) {
                            break;
                        }
                        try {
                            if (field[j][z] == 'O') {
                                throw new IllegalArgumentException();
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            }
        } else if (startCoordsColumn == endCoordsColumn) {
            for (int i = arrIndexStartRow; i <= arrIndexEndRow; ++i) {
                for (int j = i - 1; j <= i + 1; ++j) {
                    if (j < 0 || j > 9) {
                        break;
                    }
                    for (int z = arrIndexStartColumn - 1; z <= arrIndexStartColumn + 1; ++z) {
                        if (z < 0 || z > 9) {
                            break;
                        }
                        try {
                            if (field[j][z] == 'O') {
                                throw new IllegalArgumentException();
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private void putShipInArr(char startCoordsRow, int startCoordsColumn,
                              char endCoordsRow, int endCoordsColumn) {
        int arrIndexStartRow = (int)(startCoordsRow - 65);
        int arrIndexEndRow = (int)(endCoordsRow - 65);
        int arrIndexStartColumn = startCoordsColumn - 1;
        int arrIndexEndColumn = endCoordsColumn - 1;
        for (int i = arrIndexStartRow; i <= arrIndexEndRow; ++i) {
            for (int j = arrIndexStartColumn; j <= arrIndexEndColumn; ++j)
                field[i][j] = 'O';
        }
    }

    private void putAircraftCarrier(boolean print) {

        if (print) {
            System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        }

        boolean successfull = putShip(5, "Aircraft Carrier");
        if (!successfull) {
            putAircraftCarrier(false);
        }
    }

    private void putBattleship(boolean print) {

        if (print) {
            System.out.println("Enter the coordinates of the Battleship (4 cells):");
        }

        boolean successfull = putShip(4, "Battleship");
        if (!successfull) {
            putBattleship(false);
        }
    }

    private void putSubmarine(boolean print) {

        if (print) {
            System.out.println("Enter the coordinates of the Submarine (3 cells):");
        }

        boolean successfull = putShip(3, "Submarine");
        if (!successfull) {
            putSubmarine(false);
        }
    }

    private void putCruiser(boolean print) {

        if (print) {
            System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        }

        boolean successfull = putShip(3, "Cruiser");
        if (!successfull) {
            putCruiser(false);
        }
    }

    private void putDestroyer(boolean print) {

        if (print) {
            System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        }

        boolean successfull = putShip(2, "Destroyer");
        if (!successfull) {
            putDestroyer(false);
        }
    }

    public void putAllShips() {
        printField(field);
        putAircraftCarrier(true);
        putBattleship(true);
        putSubmarine(true);
        putCruiser(true);
        putDestroyer(true);
    }

    public void shoot() {

        printField(foggedEnemyField);
        System.out.println("---------------------");
        printField(field);

        System.out.printf("%s, it's your turn:\n", playerName);

        String coords = scanner.next();
        char row = coords.charAt(0);
        int column = Integer.parseInt(coords.substring(1, coords.length()));

        int arrIndexRow = row - 65;
        int arrIndexColumn = column - 1;

        try {
            if (arrIndexRow < 0 || arrIndexRow > 9 ||
                    arrIndexColumn < 0 || arrIndexColumn > 9) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            shoot();
        }

        if (enemyField[arrIndexRow][arrIndexColumn] != 'O' && enemyField[arrIndexRow][arrIndexColumn] != 'X') {
            enemyField[arrIndexRow][arrIndexColumn] = 'M';
            foggedEnemyField[arrIndexRow][arrIndexColumn] = 'M';
            enemyField[arrIndexRow][arrIndexColumn] = 'M';
            System.out.println("You missed!");
            Main.pressEnterKey();
            return;
        } else {
            if (enemyField[arrIndexRow][arrIndexColumn] == 'O') {
                ++counter;
            }
            enemyField[arrIndexRow][arrIndexColumn] = 'X';
            foggedEnemyField[arrIndexRow][arrIndexColumn] = 'X';
            if (counter == 17) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                gameIsOver = true;
                return;
            }
            if (checkShipSunk(coords)) {
                System.out.println("You sank a ship!");
            } else {
                System.out.println("You hit a ship!");
            }
            Main.pressEnterKey();
            return;
        }

    }

    @Deprecated
    public void start() {
        System.out.println("The game starts!");
        printField(foggedField);
        shoot();
    }

    /*enum Ship {
        AIRCRAFTCARRIER(5),
        BATTLESHIP(4),
        SUBMARINE(3),
        CRUISER(3),
        DESTROYER(2);

        String[] shipCells;
        int cellsHit = 0;

        Ship(int size) {
            shipCells = new String[size];
        }

        public String[] getShipCells() {
            return shipCells;
        }

        public void fillShipCells(String startCoords, String endCoords) {
            int length = shipCells.length;
            char startCoordsRow = startCoords.charAt(0);
            int startCoordsColumn = Integer.parseInt(startCoords.substring(1, startCoords.length()));
            char endCoordsRow = endCoords.charAt(0);
            int endCoordsColumn = Integer.parseInt(endCoords.substring(1, endCoords.length()));

            if (startCoordsRow > endCoordsRow) {
                char tempStart = startCoordsRow;
                startCoordsRow = endCoordsRow;
                endCoordsRow = tempStart;
            }
            if (startCoordsColumn > endCoordsColumn) {
                int tempStart = startCoordsColumn;
                startCoordsColumn = endCoordsColumn;
                endCoordsColumn = tempStart;
            }

            if (startCoordsRow == endCoordsRow) {
                for (int i = 0; i < length; ++i) {
                    shipCells[i] = "" + startCoordsRow + (startCoordsColumn + i);
                }
            } else if (startCoordsColumn == endCoordsColumn) {
                for (int i = 0; i < length; ++i) {
                    shipCells[i] = "" + (char)(startCoordsRow + i) + startCoordsColumn;
                }
            }

        }
    }*/

    public boolean checkShipSunk(String coords) {

        for (int i = 0; i < 5; ++i) {
            if (coords.equals(enemyFieldObj.aircraftCarrier.shipCells[i])) {
                ++enemyFieldObj.aircraftCarrier.cellsHit;
                enemyFieldObj.aircraftCarrier.shipCells[i] = "Was hit";
                if (enemyFieldObj.aircraftCarrier.cellsHit == 5) {
                    return true;
                }
            }
        }

        for (int i = 0; i < 4; ++i) {
            if (coords.equals(enemyFieldObj.battleship.shipCells[i])) {
                ++enemyFieldObj.battleship.cellsHit;
                enemyFieldObj.battleship.shipCells[i] = "Was hit";
                if (enemyFieldObj.battleship.cellsHit == 4) {
                    return true;
                }
            }
        }
        System.out.println(coords);
        for (int i = 0; i < 4; ++i) {
            System.out.println(enemyFieldObj.battleship.shipCells[i]);
        }

        for (int i = 0; i < 3; ++i) {
            if (coords.equals(enemyFieldObj.submarine.shipCells[i])) {
                ++enemyFieldObj.submarine.cellsHit;
                enemyFieldObj.submarine.shipCells[i] = "Was hit";
                if (enemyFieldObj.submarine.cellsHit == 3) {
                    return true;
                }
            }
        }

        for (int i = 0; i < 3; ++i) {
            if (coords.equals(enemyFieldObj.cruiser.shipCells[i])) {
                ++enemyFieldObj.cruiser.cellsHit;
                enemyFieldObj.cruiser.shipCells[i] = "Was hit";
                if (enemyFieldObj.cruiser.cellsHit == 3) {
                    return true;
                }
            }
        }

        for (int i = 0; i < 2; ++i) {
            if (coords.equals(enemyFieldObj.destroyer.shipCells[i])) {
                ++enemyFieldObj.destroyer.cellsHit;
                enemyFieldObj.destroyer.shipCells[i] = "Was hit";
                if (enemyFieldObj.destroyer.cellsHit == 3) {
                    return true;
                }
            }
        }

        return false;

    }

}
