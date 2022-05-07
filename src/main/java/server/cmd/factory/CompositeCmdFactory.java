package server.cmd.factory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import server.cmd.Cmd;

/**
 * sCmd 를 입력 받고 factories 를 통해 적절한 cmd 객체를 생성하는 역할.
 */
public class CompositeCmdFactory implements Factory{
    private final List<Factory> factories;

    public CompositeCmdFactory(List<Factory> factories) {
        this.factories = validate(factories);
    }

    private static List<Factory> validate(List<Factory> factories){
        if(Objects.isNull(factories)){
            throw new RuntimeException("Factory is null.");
        }

        List<Factory> newFactories = factories.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if(newFactories.isEmpty()){
            throw new RuntimeException("Factory is empty.");
        }
        return newFactories;
    }

    @Override
    public boolean isCreatable(String cmd) {
        return factories.stream().anyMatch(f->f.isCreatable(cmd));
    }

    @Override
    public Cmd create(String cmd) {
        return factories.stream()
            .filter(f->f.isCreatable(cmd))
            .findFirst()
            .map(f->f.create(cmd))
            .orElseThrow(()->new RuntimeException("Not exist matched cmd."));
    }
}
