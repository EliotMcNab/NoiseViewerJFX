package app.noiseviewerjfx.io;

public enum FileType {

    ALL_FILE_TYPE(""),
    JPG(".jpg"),
    PNG(".png"),
    TXT(".txt");

    private final String fileType;

    FileType(String fileType) {
        this.fileType = fileType;
    }

    public String extension() {
        return fileType;
    }

}
