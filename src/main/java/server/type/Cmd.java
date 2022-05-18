package server.type;

import java.util.Arrays;
import java.util.function.Predicate;

public enum Cmd {
    SEND("/send", Type.GENERIC), NOTICE("/notice", Type.NOTICE);

    private final String cmd;
    private final Cmd.Type type;

    Cmd(String cmd, Type type) {
        this.cmd = cmd;
        this.type = type;
    }

    public int getTypeCode() {
        return type.code;
    }

    private boolean isGeneric() {
        return this.type == Type.GENERIC;
    }

    private boolean isNotice(){
        return this.type == Type.NOTICE;
    }

    public static boolean notGeneric(String cmd){
        return !isGeneric(cmd);
    }

    public static boolean notNotice(String cmd){
        return !isNotice(cmd);
    }

    public static boolean isGeneric(String cmd){
        return filteredAnyMatch(Cmd::isGeneric, cmd);
    }

    public static boolean isNotice(String cmd){
        return filteredAnyMatch(Cmd::isNotice, cmd);
    }

    private static boolean filteredAnyMatch(Predicate<Cmd> predicate, String cmd){
        return Arrays.stream(values())
            .filter(predicate)
            .anyMatch(cmdType -> cmdType.cmd.equalsIgnoreCase(cmd));
    }

    private enum Type{
        GENERIC(0), NOTICE(1);

        private final int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
