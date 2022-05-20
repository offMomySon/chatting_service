package common.domain;

import java.util.Objects;

/**
 * 
 * 
 * 외부 관점에서 * 이든 127.0.0.1 이든 전송할 대상을 지칭하는 데이터 이다.
 * 하지만, 내부 관점에서는 사용방식이 다르기 때문에 별도의 객체로 분리해야한다.
 * 외부 관점을 녹이기 위해 address 추상 클래스를 만들고, 내부의 사용방식에 따라 데이터를 디테일 하게 다루기 위해 추상 클래스를 상속한
 * AllMatchDestination,SpecificDestination 으로 나눈다.
 *
 *
 * ip 를 string 으로만 다루게 된다면,
 *  '*' 이든, 127.0.0.1 이든 동일하게 의도대로 동작하게 할 수 있지만,사용할 때마다 , 값 포멧의 검사가 필요합니다.
 *
 * 그렇기 때문에 객체 생성을 통해 format 을 보장하고 객체로 데이터를 전달하는 것이 좋아 보입니다.
 *
 * 성질이 다르다는 것은?
 * 사용방법이 다르다?
 *
 */
public abstract class Address {
    private final String value;

    protected Address(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(value, address.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
