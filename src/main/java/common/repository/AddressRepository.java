package common.repository;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import server.destination.Address;
import static util.IoUtil.*;

public class AddressRepository {
    private final Map<Address, ObjectOutputStream> value = new HashMap<>();

    public ObjectOutputStream get(Address address){
        return value.get(address);
    }

    public void put(Address address, OutputStream outputStream){
        value.put(address, createObjectOutputStream(outputStream));
    }

    public boolean containsKey(Address address){
        return value.containsKey(address);
    }

    public Collection<ObjectOutputStream> values(){
        return value.values();
    }

    public void remove(Address address){
        value.remove(address);
    }
}
