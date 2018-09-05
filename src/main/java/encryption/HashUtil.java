package encryption;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HashUtil {
    public static String getMd5(String fileName) throws Exception {
        String md5="";
        md5=DigestUtils.md5Hex(new FileInputStream(fileName));
        return md5;
    }
}
