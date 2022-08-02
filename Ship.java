package battleship;

public class Ship {

    String[] shipCells;
    int cellsHit = 0;

    public Ship(int size) {
        this.shipCells = new String[size];
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

}
