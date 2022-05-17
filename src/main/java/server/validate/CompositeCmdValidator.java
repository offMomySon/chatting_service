package server.validate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * cmd 를 validate 하는 역할.
 * validate 결과를 전달해준다.
 */
public class CompositeCmdValidator extends CmdValidator {
    private final List<CmdValidator> cmdValidators;

    public CompositeCmdValidator(List<CmdValidator> cmdValidators) {
        this.cmdValidators = validateCmdValidators(cmdValidators);
    }

    public static CompositeCmdValidator from(CmdValidator ...cmdValidators){
        return new CompositeCmdValidator(Arrays.asList(cmdValidators));
    }

    private static List<CmdValidator> validateCmdValidators(List<CmdValidator> cmdValidators){
        if(Objects.isNull(cmdValidators)){
            throw new RuntimeException("cmdValidators is Null.");
        }

        List<CmdValidator> newCmdValidators = cmdValidators.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if(newCmdValidators.isEmpty()){
            throw new RuntimeException("cmdValidators is empty.");
        }

        return newCmdValidators;
    }

    @Override
    public CmdValidateResult validate(String cmd) {
        List<CmdValidateResult> validateResults = cmdValidators.stream()
            .map(cmdValidator -> cmdValidator.validate(cmd))
            .filter(cmdValidateResult -> cmdValidateResult.isValid())
            .collect(Collectors.toList());

        for(CmdValidateResult cmdValidateResult : validateResults){
            if(cmdValidateResult.isValid()){
                return cmdValidateResult;
            }
        }

        /**
         * TODO
         * 생성된 실패 메세지를 정확하게 전달하고 싶다..
         */
        return getInvalidResult(validateResults);
    }

    @NotNull
    private CmdValidateResult getInvalidResult(List<CmdValidateResult> validateResults) {
        CmdValidateResult invalidResult = validateResults.stream()
            .filter(v -> v.notValid())
            .findFirst()
            .orElseGet(() -> CmdValidateResult.fail("Invalid cmd"));

        return invalidResult;
    }
}
