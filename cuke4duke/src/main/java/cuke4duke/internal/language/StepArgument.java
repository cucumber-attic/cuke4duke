package cuke4duke.internal.language;

import java.io.UnsupportedEncodingException;

public class StepArgument {
    private final String val;
    private final int byteOffset;

    public StepArgument(String val, int charOffset, String stepName) throws UnsupportedEncodingException {
        if (charOffset != -1) {
            this.byteOffset = stepName.substring(0, charOffset).getBytes("UTF-8").length;
            this.val = val;
        } else {
            this.byteOffset = -1;
            this.val = "";
        }
    }

    public String getVal() {
        return val;
    }

    public int getByteOffset() {
        return byteOffset;
    }
}
