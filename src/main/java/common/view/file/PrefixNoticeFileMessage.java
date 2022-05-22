package common.view.file;

import java.util.Map;
import server.type.Notice;

/**
 * notice 명령어를 file 에 출력하기위한 view 중에서,
 * prefix 로 노출하기 위한 view 객체.
 */
public class PrefixNoticeFileMessage  implements FileMessage {
    private final String message;

    public PrefixNoticeFileMessage(String message) {
        this.message = message;
    }

    @Override
    public String create() {
        return message;
    }
}
