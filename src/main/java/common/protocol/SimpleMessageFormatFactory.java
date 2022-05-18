package common.protocol;

import org.apache.commons.lang3.StringUtils;
import server.type.Cmd;
import server.type.Notice;
import static server.type.Notice.getNotice;

public class SimpleMessageFormatFactory {
    private static final int GENERIC_CMD_PART_SIZE = 3;
    private static final int NOTICE_CMD_PART_SIZE = 4;

    public SimpleMessageFormat create(String cmd){
        String cmdType = cmd.substring(0,cmd.indexOf(" "));

        if(Cmd.isGeneric(cmdType)){
            String[] splitCmd = cmd.split(" ", GENERIC_CMD_PART_SIZE);

            return new GenericSimpleMessageFormat(splitCmd[2]);
        }

        if(Cmd.isNotice(cmdType)){
            String[] splitCmd = cmd.split(" ", NOTICE_CMD_PART_SIZE);

            return new NoticeSimpleMessageFormat(Notice.getNotice(splitCmd[1]), splitCmd[3]);
        }

        throw new RuntimeException("Fail to create SimpleMessageFormat. Not exist cmd.");
    }
}
