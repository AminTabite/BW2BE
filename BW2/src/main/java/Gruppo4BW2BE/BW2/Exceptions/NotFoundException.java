package Gruppo4BW2BE.BW2.Exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message, UUID id) {
        super(message);
    }
}
