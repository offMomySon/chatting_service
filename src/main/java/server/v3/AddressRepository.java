package server.v3;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import server.Address;

/**
 * 지금까지 입력받은 address 관리하는 역할.
 */
public class AddressRepository {
    private final Set<Address> values = new HashSet<>();

    public void addAddress(@NonNull Address address){
        values.add(address);
    }

    public boolean deleteAddress(@NonNull Address address){
        return values.remove(address);
    }

    public List<Address> find(Collection<Address> addresses){
        return addresses.stream()
            .filter(values::contains)
            .collect(Collectors.toUnmodifiableList());
    }

    public List<Address> findAll(){
        return values.stream().collect(Collectors.toUnmodifiableList());
    }
}
