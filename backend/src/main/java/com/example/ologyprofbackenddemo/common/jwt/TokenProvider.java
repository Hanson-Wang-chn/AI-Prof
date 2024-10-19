package com.example.ologyprofbackenddemo.common.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.google.gson.GsonBuilder;
import com.xhpolaris.idlgen.basic.UserMeta;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Component;

/**
 * description token管理
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    public static UserMeta decodeToken(String rememberMeToken, String publicKeys) {
        byte[] publicKeyBytes = parsePublicKeyString(publicKeys);

        // 转换为公钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey  = keyFactory.generatePublic(keySpec);

            // 验证 JWT
            DecodedJWT decodedJWT = JWT.require(Algorithm.ECDSA256((ECPublicKey) publicKey, null))
                    .build()
                    .verify(rememberMeToken);
            log.info("JWT verification successful!");
            String string = new String(Base64.getDecoder().decode(decodedJWT.getPayload()));
            fromJson(string, UserMeta.class);

            UserMeta.Builder builder = UserMeta.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(string, builder);

            return builder.build();
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] parsePublicKeyString(String publicKeyString) {
        // 去除开头和结尾的标记，并移除换行符
        String base64PublicKey = publicKeyString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        // 将 Base64 编码的字符串解码为字节数组
        return Base64.getDecoder().decode(base64PublicKey);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return new GsonBuilder().create().fromJson(json, clazz);
    }
}
