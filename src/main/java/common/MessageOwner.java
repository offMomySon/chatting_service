package common;

import lombok.Getter;

@Getter
public enum MessageOwner {
    SERVER("[서버]"), CLIENT("[클라]"), INFO("[INFO]"), WARN("[WARN]");

    private final String value;

    MessageOwner(String value) {
        this.value = value;
    }
}
