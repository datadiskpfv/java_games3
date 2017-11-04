package uk.co.datadisk.catandmouse;

public class InvalidMazeCharacterException extends Exception {

    private static final long serialVersionUID = -5492663937100571750L;

    private static final String MESSAGE1 = "Invalid maze character";
    private static final String MESSAGE2 = " in file ";

    private char invalidCharacter;
    private String fileName;

    public InvalidMazeCharacterException(char invalidCharacter, String fileName) {
        super(MESSAGE1 + invalidCharacter + MESSAGE2 + fileName);
        this.invalidCharacter = invalidCharacter;
        this.fileName = fileName;
    }

    public char getInvalidCharacter() {
        return invalidCharacter;
    }

    public String getFileName() {
        return fileName;
    }
}
