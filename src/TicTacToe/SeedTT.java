/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231197 - Imtiyaz Shafhal Afif
 * 2 - 5026231172 - Mochamad Zhulmi Danovanz H
 * 3 - 5026231136 - Maulana Muhammad Ad-Dzikri
 */
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
