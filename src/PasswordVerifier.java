import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to Verify User Passwords
 */
public class PasswordVerifier {

    int diagResp;                                                           // Dialog response (OK, CANCEL, CLOSE)
    private String orgPwd;                                                  // Password to compare to
    private String[] tokens;                                                // User1,Pass1,User2,Pass2...
    private JPasswordField pwd = new JPasswordField();                      // Password Field

    public void readFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            tokens = scanner.nextLine().split(",");
        } catch (IOException e) { e.printStackTrace(); }
    }

    public String[] getData(File file) {
        readFile(file);
        return tokens;
    }

    /**
     * Verifies password for a specific user
     * @param user  For whom to verify password for
     * @return      Result of password verification
     */
    public boolean verifyPwd(String user) {
        orgPwd = null;
        if (user.equals("manager")) { orgPwd = tokens[0];
        } else if (user.equals("cook")) { orgPwd = tokens[1];
        } else if (user.equals("host")) { orgPwd = tokens[2];
        } else if (user.equals("waiter")) { orgPwd = tokens[3];
        } else { orgPwd = null;
        }

        pwd.setDocument(new InputLimit(10));
        pwd.addAncestorListener(new RequestFocusListener(false));
        String givenPwd;
        do {
            pwd.setText("");
            diagResp = JOptionPane.showConfirmDialog(null, pwd, "Enter Password", JOptionPane.OK_CANCEL_OPTION);
            givenPwd = getSecurePass(pwd.getPassword());                    // Given Pass to string

            if (diagResp != JOptionPane.OK_OPTION) { return false; }        // If cancel/close is clicked, exit pwd prompt
        } while (!orgPwd.equals(givenPwd));

        return true;
    }


    /**
     * Given a char array, returns encrypted string     // http://goo.gl/8bRBwR  http://goo.gl/jnEh88
     * @param passToHash    Char array password from JPassField
     * @return String       Encypted password string
     */
    public static String getSecurePass(char[] passToHash) {
        StringBuffer generatedPass = null;
        try {
            MessageDigest msgDig = MessageDigest.getInstance("SHA-512");

            ArrayList<Byte> list = new ArrayList<Byte>();
            for (int i = 0; i < passToHash.length; i++) { list.add((byte) passToHash[i]); }

            byte[] toBeHashed = new byte[list.size()];
            for(int i = 0; i < list.size(); i++){ toBeHashed[i] = list.get(i); }

            msgDig.update(toBeHashed);
            byte byteData[] = msgDig.digest();

            generatedPass = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) { generatedPass.append('0'); }
                generatedPass.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPass.toString();
    }
}
