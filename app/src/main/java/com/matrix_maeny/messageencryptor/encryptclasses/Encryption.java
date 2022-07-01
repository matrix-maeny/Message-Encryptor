package com.matrix_maeny.messageencryptor.encryptclasses;

public class Encryption {

    public static String encryptMessage(String msg) {

        String init = "11111111";
        int cu = 0;

        int[] arr = new int[11111111]; // a temp array for storing character's ascii values

        for (int i = 0; i < msg.length(); i++) { // storing ascii values of each character in the message
            arr[i] = msg.charAt(i);
            cu++;
        }

        String res = "";
        int[] bin = new int[111];
        int idx = 0;

        for (int i = 0; i < cu; i++) {
            int temp = arr[i];

            try {
                for (int j = 0; j < cu; j++) bin[j] = 0;
            } catch (Exception ignored) {
            }

            idx = 0;

            while (temp > 0) {
                bin[idx] = temp % 2;
                temp /= 2;
                idx++;
            }

            String digs = "";
            String temps;

            for (int j = 0; j < 7; j++) {

                temps = Integer.toString(bin[j]);
                digs = digs.concat(temps);
            }

            String revs = "";

            for (int j = digs.length() - 1; j >= 0; j--) {

                char ca = digs.charAt(j);
                revs = revs.concat(String.valueOf(ca));
            }

            res = res.concat(revs);

        }

        res = init.concat(res);

        return res;
    }
}
