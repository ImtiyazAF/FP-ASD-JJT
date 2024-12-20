package TicTacToe;

public enum SeedTT {
    CROSSTT("X"),
    NOUGHTTT("O"),
    NO_SEEDTT(" ");

    // Private variable to hold the icon
    private final String icon;

    // Constructor (enum constructors are always private by default)
    SeedTT(String icon) {
        this.icon = icon;
    }

    // Public getter to retrieve the icon
    public String getIcon() {
        return icon;
    }
}
