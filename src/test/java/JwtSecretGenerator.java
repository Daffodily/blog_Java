import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        // 生成256位(32字节)的随机密钥
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);

        // 转换为Base64编码的字符串，方便存储和使用
        String base64Key = Base64.getEncoder().encodeToString(key);

        System.out.println("生成的JWT密钥 (Base64编码):");
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        System.out.println(Arrays.toString(keyBytes));
    }
}
