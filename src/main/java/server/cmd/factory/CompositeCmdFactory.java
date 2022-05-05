package server.cmd.factory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import server.cmd.Cmd;

public class CompositeCmdFactory implements Factory{
    private final List<Factory> factories;

    public CompositeCmdFactory(List<Factory> factories) {
        this.factories = validate(factories);
    }

    private List<Factory> validate(List<Factory> factories){
        if(Objects.isNull(factories)){
            throw new RuntimeException("factory 가 null 입니다.");
        }

        List<Factory> newFactories = factories.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if(newFactories.isEmpty()){
            throw new RuntimeException("factory 가 empty 입니다.");
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
            .orElseThrow(()->new RuntimeException("생성가능한 cmd 가 없습니다."));
    }
}
