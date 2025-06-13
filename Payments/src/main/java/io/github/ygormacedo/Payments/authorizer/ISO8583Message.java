package io.github.ygormacedo.Payments.authorizer;

import javax.print.attribute.standard.MediaSize;
import java.util.HashMap;
import java.util.Map;

public class ISO8583Message {
    private String mti;
    private Map<Integer, String> fields = new HashMap<>();

    public ISO8583Message() {}

    public ISO8583Message(String rawMessage) {
        parse(rawMessage);
    }

    public String getMTI() {
        return mti;
    }

    public void setMTI(String mti) {
        this.mti = mti;
    }

    public String getField(int fieldNumber) {
        return fields.get(fieldNumber);
    }

    public void setField(int fieldNumber, String value) {
        fields.put(fieldNumber, value);
    }

    private void parse(String rawMessage) {

        // Implementação simplificada do parser ISO8583

        String[] parts = rawMessage.split("\\|");
        this.mti = parts[0];

        for (int i = 1; i < parts.length; i++) {
            String[] fieldParts = parts[i].split("=", 2);
            if (fieldParts.length == 2) {
                fields.put(Integer.parseInt(fieldParts[0]), fieldParts[1]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mti);

        for (Map.Entry<Integer, String> entry : fields.entrySet()) {
            sb.append("|").append(entry.getKey()).append("=").append(entry.getValue());
        }

        return sb.toString();
    }
}
