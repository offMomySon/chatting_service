package server.sender.factory;

import common.repository.AddressRepository;
import common.view.format.SimpleMessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import server.destination.Address;
import server.sender.AllSmfSmfSender;
import server.sender.IpSmfSmfSender;
import server.sender.MultiIpSmfSmfSender;
import server.sender.SmfSender;
import server.sender.validator.AddressValidator;

public class SmfSendStrategyFactory {
    private static final List<String> ALL_ADDRESS = List.of("*", "all");
    private static final String IP_ADDRESSES_DELIMITER = ",";

    private final AddressValidator addressValidator;
    private final AddressRepository addressRepository;
    private final SimpleMessageFormat simpleMessageFormat;

    public SmfSendStrategyFactory(@NonNull AddressValidator addressValidator, @NonNull AddressRepository addressRepository, @NonNull SimpleMessageFormat simpleMessageFormat) {
        this.addressValidator = addressValidator;
        this.addressRepository = addressRepository;
        this.simpleMessageFormat = simpleMessageFormat;
    }

    public SmfSender create(String address){
        if(addressValidator.notValid(address)){
            throw new RuntimeException("올바르지 않은 address 입니다.");
        }

        if(isAllAddress(address)) {
            return new AllSmfSmfSender(addressRepository, simpleMessageFormat);
        }

        List<Address> addresses = Arrays.stream(address.split(IP_ADDRESSES_DELIMITER)).map(Address::new).collect(
            Collectors.toList());

        if(addresses.size() == 1){
            return new IpSmfSmfSender(addressRepository, addresses.get(0), simpleMessageFormat);
        }

        return new MultiIpSmfSmfSender(addressRepository, addresses, simpleMessageFormat);
    }

    private static boolean isAllAddress(String address){
        return ALL_ADDRESS.stream()
            .anyMatch(_address -> StringUtils.equalsIgnoreCase(_address, address));
    }
}
