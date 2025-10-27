package Gruppo4BW2BE.BW2.Exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, UUID id) {
        super(message);}

    public NotFoundException(String message) {
    }
}
