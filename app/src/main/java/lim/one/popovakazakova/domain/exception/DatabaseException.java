package lim.one.popovakazakova.domain.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String detailMessage) {
        super(detailMessage);
    }
}
