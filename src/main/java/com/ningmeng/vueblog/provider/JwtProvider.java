package com.ningmeng.vueblog.provider;

import com.ningmeng.vueblog.config.Audience;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @Author: ningmengmao
 * @Date: 2019/10/20 下午2:38
 * @Version 1.0
 */
@Slf4j
@Component
public class JwtProvider {

    private static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";

    public static Claims parseJWT(String jwtMessage, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jwtMessage)
                    .getBody();
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("===== Token过期 =====");
            throw new RuntimeException("token过期");
        } catch (Exception e) {
            log.error("===== Token解析异常 =====");
            throw new RuntimeException("token解析异常");
        }
    }

    public static String createJWT(int userId, String username, String role, Audience audience) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date date = new Date(nowMillis);
            byte[] apiKeySecretBytes = Base64.getDecoder().decode(audience.getBase64Secret());
            SecretKeySpec secretKeySpec = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


            String encryUserId = Base64.getEncoder().encodeToString(String.valueOf(userId).getBytes());
            String jws = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("alg", "HS256")
                    .claim("role", role)
                    .setId(encryUserId)
                    .setSubject(audience.getName())
                    .setIssuer(audience.getName())
                    .setIssuedAt(date)
                    .setAudience(username)
                    .setExpiration(new Date(System.currentTimeMillis() + audience.getExpiresSecond() * 1000))
                    .signWith(secretKeySpec)
                    .compact();
            return jws;
        } catch (Exception e) {
            log.error("===== 创建token失败 ===== exception:" + e.getClass().getSimpleName());
            throw new RuntimeException("创建token失败");
        }
    }


    /**
     * 随机生成密钥对
     *
     * @return
     */
    public static Map<String, String> getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator rsaKeyPairGen = KeyPairGenerator.getInstance("RSA");
        rsaKeyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = rsaKeyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        HashMap<String, String> map = new HashMap<>(2);
        map.put("publicKey", publicKeyString);
        map.put("privateKey", privateKeyString);
        return map;
    }


    /**
     * RSA公钥加密
     *
     * @param content   加密内容
     * @param publicKey 公钥
     * @return 进过rsa加密, 在base64编码的密文
     * @throws InvalidKeyException
     */
    public static String encrypt(String content, String publicKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        byte[] key = Base64.getDecoder().decode(publicKey);
        PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(key));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }


    /**
     * RSA私钥解密
     *
     * @param content    加密的信息
     * @param privateKey 私钥
     * @return 铭文
     */
    public static String decrypt(String content, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException {
        byte[] contentDecode = Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8));
        byte[] privateKeyDecode = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyDecode));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        String result = new String(cipher.doFinal(contentDecode));
        return result;
    }
}
