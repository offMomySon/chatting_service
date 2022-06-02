package common.protocal;

import common.protocal.generic.GenericSimpleMessageFormat;
import common.protocal.notice.NoticeInfoSimpleMessageFormat;
import server.type.Cmd;
import server.type.Notice;

/**
 * smp 를 생성하는 역할, 책임 을 가지는 객체.
 */

public class SimpleMessageFormatFactory {
    private static final String CMD_DELIMITER = " ";
    private static final int GENERIC_CMD_PART_SIZE = 3;
    private static final int NOTICE_CMD_PART_SIZE = 4;

    public SimpleMessageFormat create(String sCmd) {
        String cmdType = getCmdType(sCmd);

        Cmd cmd = Cmd.from(cmdType).orElseThrow(() -> new RuntimeException("Not exist cmd."));

        switch (cmd) {
            case SEND: {
                String[] splitCmd = sCmd.split(CMD_DELIMITER, GENERIC_CMD_PART_SIZE);

                return new GenericSimpleMessageFormat(splitCmd[2]);
            }
            case NOTICE: {
                String[] splitCmd = sCmd.split(CMD_DELIMITER, NOTICE_CMD_PART_SIZE);

                Notice notice = Notice.from(splitCmd[1]).orElseThrow(() -> new RuntimeException("Not exist notice."));
                String message = splitCmd[3];

                switch (notice) {
                    case INFO:
                        return new NoticeInfoSimpleMessageFormat(message);
                    case WARN:
                        return new GenericSimpleMessageFormat(message);
                }
            }
        }

        throw new RuntimeException("Not exist sCmd");
    }

    private String getCmdType(String cmd) {
        return cmd.substring(0, cmd.indexOf(CMD_DELIMITER));
    }
}
