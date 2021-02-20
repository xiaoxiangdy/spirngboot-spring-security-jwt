package com.practice.praproject.jwt;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.practice.praproject.pojo.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Component
public class JWTUtils {

    public static final String JWT_ID = UUID.randomUUID().toString();

    // 加密密文，私钥
    @Value("${jwt.sign}")
    public  String SIGN;

    @Value("${jwt.expire_time}")
    public  int expireTime;


    // 由字符串生成加密key
    public  SecretKey generalKey() {
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(SIGN);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
    // 创建jwt
    public  String createJWT(String username, String userId, List<Role> roles, String audience) throws Exception {
        // 设置头部信息
//		Map<String, Object> header = new HashMap<String, Object>();
//		header.put("typ", "JWT");
//		header.put("alg", "HS256");
        // 或
        // 指定header那部分签名的时候使用的签名算法，jjwt已经将这部分内容封装好了，只有{"alg":"HS256"}
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证的方式）
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("userId",userId);
        claims.put("roles", JSONArray.toJSONString(roles));
        // jti用户id，例如：20da39f8-b74e-4a9b-9a0f-a39f1f73fe64
        String jwtId = JWT_ID;
        // 生成JWT的时间
        long nowTime = System.currentTimeMillis();
        Date issuedAt = new Date(nowTime);
        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露，是你服务端的私钥，在任何场景都不应该流露出去，一旦客户端得知这个secret，那就意味着客户端是可以自我签发jwt的
        SecretKey key = generalKey();
        // 为payload添加各种标准声明和私有声明
        JwtBuilder builder = Jwts.builder() // 表示new一个JwtBuilder，设置jwt的body
//				.setHeader(header) // 设置头部信息
                .setClaims(claims)// 如果有私有声明，一定要先设置自己创建的这个私有声明，这是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明
                .setId(jwtId) // jti(JWT ID)：jwt的唯一身份标识，根据业务需要，可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击
                .setIssuedAt(issuedAt) // iat(issuedAt)：jwt的签发时间
                .setIssuer("xx") // iss(issuer)：jwt签发者
                .setSubject("all") // sub(subject)：jwt所面向的用户，放登录的用户名，一个json格式的字符串，可存放userid，roldid之类，作为用户的唯一标志
                .signWith(signatureAlgorithm, key); // 设置签名，使用的是签名算法和签名使用的秘// 钥
        // 设置过期时间
        long expTime = expireTime;
        if (expTime >= 0) {
            long exp = nowTime + expTime;
            builder.setExpiration(new Date(exp));
        }
        // 设置jwt接收者
        if (audience == null || "".equals(audience)) {
            builder.setAudience("Tom");
        } else {
            builder.setAudience(audience);
        }
        return builder.compact();
    }


    // 解密jwt
    public Claims parseJWT(String jwt) throws Exception {
        Claims claims = null;
        if (StringUtils.isNotBlank(jwt)) {
            SecretKey key = generalKey(); // 签名秘钥，和生成的签名的秘钥一模一样
            claims = Jwts.parser() // 得到DefaultJwtParser
                    .setSigningKey(key)// 设置签名的秘钥
                    .parseClaimsJws(jwt).getBody(); // 设置需要解析的jwt
        }
        return claims;
    }

    /**
     * 是否过期
     * @param token
     * @return
     */
    public  boolean isExpiration(String token){
        Claims claims = Jwts.parser().setSigningKey(SIGN).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

}
