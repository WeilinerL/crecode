package cn.crecode.blog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtToken {
    /**
     * APP登录Token的生成和解析
     *
     */

    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
    public static final String SECRET = "JKKLJOoasdlfj<>?<asdahbdwq354467)+_+{}ads567322asdERT123RDY~UYGUY678";
    /** token 过期时间: 7天 */
    public static final int calendarField = Calendar.DATE;
    // token 有效期七天
    public static final int calendarInterval = 7;

    private final static Log log = LogFactory.getLog(JwtToken.class);

    /**
     * JWT生成Token.<br/>
     *
     * JWT构成: header, payload, signature
     *
     * @param user_id
     *            登录成功后用户user_id, 参数user_id不可传空
     */
    public static String createToken(String user_id) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload iss 签发者 aud接收token用户
                .withClaim("aud", "APP").withClaim("user_id", null == user_id ? null : user_id)
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
            if(log.isTraceEnabled()) {
                log.trace("token效验失败!");
            }
            //System.out.println("token效验失败!");
            return null;
//            throw new Exception("token效验失败!");
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static Long getAppUID(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("user_id");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
            if(log.isTraceEnabled()) {
                log.trace("token非法异常!");
            }
            //throw new Exception("token非法异常!");
        }
        return Long.valueOf(user_id_claim.asString());
    }
}
