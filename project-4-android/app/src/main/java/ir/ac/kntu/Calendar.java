package ir.ac.kntu;

import java.time.Instant;

public final class Calendar {
    public static final int TIME_SPEED = 60000;

    private static Instant start = Instant.now();

    private Calendar() {
    }

    public static Instant now() {
        return Instant.ofEpochMilli(start.toEpochMilli() + (Instant.now().toEpochMilli() - start.toEpochMilli()) * TIME_SPEED);
    }
}