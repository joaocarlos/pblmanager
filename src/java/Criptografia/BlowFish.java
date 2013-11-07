/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Criptografia;

import javax.crypto.*;
import javax.crypto.spec.*;

/**
 *
 * @author larissa
 */
public class BlowFish {
    public static final String cript(String str, String passfrase) {
        String strCript = str;
        try {
            Cipher ch = Cipher.getInstance("Blowfish");
            SecretKey k1 = new SecretKeySpec(passfrase.getBytes(), "Blowfish");
            ch.init( Cipher.ENCRYPT_MODE, k1);
            byte b[] = ch.doFinal(strCript.getBytes());
            String s1 = Conversion.byteArrayToBase64String(b);
            strCript = s1;
        } catch( Exception e) {
            e.printStackTrace();
        }
        return strCript;
    }

    public static final String decript(String str, String passfrase) {
        String strDecript = str;
        try {
            Cipher ch = Cipher.getInstance("Blowfish");
            SecretKey k1 = new SecretKeySpec(passfrase.getBytes(), "Blowfish");

            //decriptografando
            ch.init( Cipher.DECRYPT_MODE, k1);
            byte b[] = Conversion.base64StringToByteArray(strDecript);
            byte b1[] = ch.doFinal(b);
            String s1 = new String(b1);
            strDecript = s1;
        } catch( Exception e) {
            e.printStackTrace();
        }
        return strDecript;
    }

    static public void main(String[] args) {
        String cript = BlowFish.cript("senha", "login");
        String decript = BlowFish.decript(cript, "login");

        System.out.println("Cripto: " + "--->" + cript + "<---");
        System.out.println("Decripto: " + decript);

    }
}