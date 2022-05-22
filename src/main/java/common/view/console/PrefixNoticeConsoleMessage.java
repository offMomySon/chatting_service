package common.view.console;

import java.util.Map;
import server.type.Notice;

/**
 * notice 명령어를 console 에 노출하기 위한 view 중에서,
 * prefix 로 노출하기 위한 메세지의 view 의 객체.
 */
public class PrefixNoticeConsoleMessage implements ConsoleMessage {
    private final String value;

    public PrefixNoticeConsoleMessage(String value) {
        this.value = value;
    }

    @Override
    public String create() {
        return value;
    }
}
/**
 * 생각 - Prefix 를 생성하는 방법들에 대한 고민.
 *
 * 1. NOTICE enum 에서 데이터를 가져오는 경우
 *      (1) view 의 데이터가 enum 에 존재하게 된다.
 *      (2) 데이터가 enum 에 존재하게 됨으로써 view 객체들은 enum 에 종속적일 수 밖에 없다.
 *      (3) 객체의 의도가 view 이지만, 사실 enum 데이터의 연결 객체가 되어 버린다.
 *          -> view 의 컨셉이 사라진다.
 *          -> view 는 view 만을 위한 데이터를 따로 가지고 있어야 하는데 그러지 못하게 된다.
 *
 * 2. FM 에서 명령어를 가져오는 경우.
 *     PrefixNoticeConsoleMessge 의 fm 에서 받아온 Notice type 에 따라 명령어를 설정한 경우.
 *     private static final Map<Notice, String> NOTICE_CONSOLE_VIEW = Map.of(Notice.INFO, "\\u001B[33m[INFO]\\u001B[0m",
 *                                                                            Notice.WARN, "\\u001B[31m[WARN]\\u001B[0m");
 *      (1) data 인 notice 종속성을 끊어내지 못한다. 왜냐하면 fm 에서 Notice 타입에 따라 생성하기 위해 notice 를 보기 때문에.
 *      (2) NoticeInfoFileMessage, NoticeWarnFileMeeasge 개념을 생성할 이유가 없어진다.
 *          NoticeMessage 객체에서 Notice 을 받아서 바로 생성하면 되지 굳이 개념을 생성할 필요가 없다.
 *          -> 순수한 view 의 개념이 아닌 데이터를 보고 view 를 생성하게 된다.
 *
 * 3. Prefix 생성자에서 view 를 지정하는 경우.
 *      (1) low 데이터를 모두 끊어내고 순수한 view 객체로 이용가능하다.
 *      (2) NoticeInfoFileMessage, NoticeWarn fileMessage 등 상세한 개념 prefix 개념을 이용해서 생성 할 수 있다.
 *          public class NoticeInfoFileMessage extends NoticeFileMessage {
 *              super(new PrefixNoticeFileMessage("[INFO]"), message);
 *          }
 *
 * 4. prefix 개념을 왜 만들어야 할까?
 * class 를 통해 문맥을 추가하기 위해?
 */
