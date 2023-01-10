class WordNotFoundException extends Exception {
    public WordNotFoundException(String errorMessage) {
        super(errorMessage);
    }
    public WordNotFoundException() {
        super();
    }
}
