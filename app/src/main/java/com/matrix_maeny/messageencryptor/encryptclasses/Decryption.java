package com.matrix_maeny.messageencryptor.encryptclasses;

public class Decryption {

    public static String decryptedMessage = null;

    public static boolean decryptMessage(String encryptedMsg){

        String invalid = "Invalid code";

        String ini = "11111111";
        boolean flag = true;

        for (int i = 0; i < 8; i++) {
            // check if the initial value is same
            if (ini.charAt(i) != encryptedMsg.charAt(i)) {
                flag = false;
                break;
            }
        }
        String val = "";

        for (int i = 8; i < encryptedMsg.length(); i++) {
            char ch = encryptedMsg.charAt(i);
            val = val.concat(String.valueOf(ch));
        }

        int[][] arr = new int[11101][8];
        int ind1 = -1;
        int ind2 = 0;

        for (int i = 0; i < val.length(); i++) {

            // check if the position of the
            // string if divisible by 7
            if (i % 7 == 0) {
                // start the value in other
                // column of the 2D array
                ind1++;
                ind2 = 0;
                char ch = val.charAt(i);
                arr[ind1][ind2] = ch - '0';
            } else {
                // otherwise store the value
                // in the same column
                char ch = val.charAt(i);
                arr[ind1][ind2] = ch - '0';
            }
            ind2++;
        }

        int[] num = new int[11111];
        int nind = 0;
        int tem = 0;
        int cu = 0;

        for (int i = 0; i <= ind1; i++) {
            cu = 0;
            tem = 0;
            // convert binary to decimal and add them
            // from each column and store in the array
            for (int j = 6; j >= 0; j--) {
                int tem1 = (int) Math.pow(2, cu);
                tem += (arr[i][j] * tem1);
                cu++;
            }
            num[nind++] = tem;
        }

        String ret = "";
        char ch;

        for (int i = 0; i < nind; i++) {
            ch = (char) num[i];
            ret = ret.concat(String.valueOf(ch));
        }

        if (val.length() % 7 == 0 && flag) {
            // return the decrypted code
            decryptedMessage = ret;
            return true;
        } else {
            // otherwise return an invalid message
            decryptedMessage = invalid;
            return false;
        }
    }
}
