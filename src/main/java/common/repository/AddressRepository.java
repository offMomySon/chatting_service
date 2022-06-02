package common.repository;

import java.io.BufferedWriter;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import server.destination.Address;
import static util.IoUtil.*;

public class AddressRepository {
    private final Map<Address, BufferedWriter> value = new HashMap<>();

    public BufferedWriter get(Address address){
        return value.get(address);
    }

    public void put(Address address, OutputStream outputStream){
        value.put(address, createWriter(outputStream));
    }

    public Collection<BufferedWriter> values(){
        return value.values();
    }
}
