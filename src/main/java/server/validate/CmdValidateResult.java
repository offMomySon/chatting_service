package server.validate;

/**
 * cmd validate 결과 전달을 위한 DTO 객체
 */
public class CmdValidateResult {
    private final boolean isValid;
    private final String msg;

    private CmdValidateResult(boolean isValid, String msg) {
        this.isValid = isValid;
        this.msg = msg;
    }

    public static CmdValidateResult fail(String msg){
        return new CmdValidateResult(false, msg);
    }

    public static CmdValidateResult success(){
        return new CmdValidateResult(true, "success");
    }

    public boolean isValid(){
        return isValid;
    }

    public boolean notValid(){
        return !isValid();
    }

    public String getMsg() {
        return msg;
    }
}
