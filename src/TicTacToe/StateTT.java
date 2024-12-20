package TicTacToe;

public enum StateTT {
    PLAYINGTT,    // Representing state where the game is ongoing
    DRAWTT,       // Representing a draw
    CROSS_WONTT,  // Representing CROSS winning the game
    NOUGHT_WONTT; // Representing NOUGHT winning the game

    // The current state of the game
    public static StateTT currentState = StateTT.PLAYINGTT;   // Assigned to a named constant, which is easier to read
    // and understand, instead of an int number 0
}
