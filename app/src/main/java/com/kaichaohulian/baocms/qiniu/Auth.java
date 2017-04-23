package com.kaichaohulian.baocms.qiniu;

/**
 * Created by huashao on 2016/10/29.
 */


import java.net.URI;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public final class Auth {
    private static final String[] policyFields = new String[]{"callbackUrl", "callbackBody", "callbackHost", "callbackBodyType", "callbackFetchKey", "returnUrl", "returnBody", "endUser", "saveKey", "insertOnly", "detectMime", "mimeLimit", "fsizeLimit", "fsizeMin", "persistentOps", "persistentNotifyUrl", "persistentPipeline", "deleteAfterDays"};
    private static final String[] deprecatedPolicyFields = new String[]{"asyncOps"};
    private final String accessKey;
    private final SecretKeySpec secretKey;

    private Auth(String accessKey, SecretKeySpec secretKeySpec) {
        this.accessKey = accessKey;
        this.secretKey = secretKeySpec;
    }

    public static Auth create(String accessKey, String secretKey) {
        if(!StringUtils.isNullOrEmpty(accessKey) && !StringUtils.isNullOrEmpty(secretKey)) {
            byte[] sk = StringUtils.utf8Bytes(secretKey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(sk, "HmacSHA1");
            return new Auth(accessKey, secretKeySpec);
        } else {
            throw new IllegalArgumentException("empty key");
        }
    }

    private static void copyPolicy(final StringMap policy, StringMap originPolicy, final boolean strict) {
        if(originPolicy != null) {
            originPolicy.forEach(new StringMap.Consumer() {
                public void accept(String key, Object value) {
                    if(StringUtils.inStringArray(key, deprecatedPolicyFields)) {
                        throw new IllegalArgumentException(key + " is deprecated!");
                    } else {
                        if(!strict || StringUtils.inStringArray(key, policyFields)) {
                            policy.put(key, value);
                        }

                    }
                }
            });
        }
    }

    private Mac createMac() {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(this.secretKey);
            return mac;
        } catch (GeneralSecurityException var3) {
            var3.printStackTrace();
            throw new IllegalArgumentException(var3);
        }
    }

    public String sign(byte[] data) {
        Mac mac = this.createMac();
        String encodedSign = UrlSafeBase64.encodeToString(mac.doFinal(data));
        return this.accessKey + ":" + encodedSign;
    }

    public String sign(String data) {
        return this.sign(StringUtils.utf8Bytes(data));
    }

    public String signWithData(byte[] data) {
        String s = UrlSafeBase64.encodeToString(data);
        return this.sign(StringUtils.utf8Bytes(s)) + ":" + s;
    }

    public String signWithData(String data) {
        return this.signWithData(StringUtils.utf8Bytes(data));
    }

    public String signRequest(String urlString, byte[] body, String contentType) {
        URI uri = URI.create(urlString);
        String path = uri.getRawPath();
        String query = uri.getRawQuery();
        Mac mac = this.createMac();
        mac.update(StringUtils.utf8Bytes(path));
        if(query != null && query.length() != 0) {
            mac.update((byte)63);
            mac.update(StringUtils.utf8Bytes(query));
        }

        mac.update((byte)10);
        if(body != null && body.length > 0 && !StringUtils.isNullOrEmpty(contentType) && (contentType.equals("application/x-www-form-urlencoded") || contentType.equals("application/json"))) {
            mac.update(body);
        }

        String digest = UrlSafeBase64.encodeToString(mac.doFinal());
        return this.accessKey + ":" + digest;
    }

    public boolean isValidCallback(String originAuthorization, String url, byte[] body, String contentType) {
        String authorization = "QBox " + this.signRequest(url, body, contentType);
        return authorization.equals(originAuthorization);
    }

    public String privateDownloadUrl(String baseUrl) {
        return this.privateDownloadUrl(baseUrl, 3600L);
    }

    public String privateDownloadUrl(String baseUrl, long expires) {
        long deadline = System.currentTimeMillis() / 1000L + expires;
        return this.privateDownloadUrlWithDeadline(baseUrl, deadline);
    }

    String privateDownloadUrlWithDeadline(String baseUrl, long deadline) {
        StringBuilder b = new StringBuilder();
        b.append(baseUrl);
        int pos = baseUrl.indexOf("?");
        if(pos > 0) {
            b.append("&e=");
        } else {
            b.append("?e=");
        }

        b.append(deadline);
        String token = this.sign(StringUtils.utf8Bytes(b.toString()));
        b.append("&token=");
        b.append(token);
        return b.toString();
    }

    public String uploadToken(String bucket) {
        return this.uploadToken(bucket, null, 3600L, null, true);
    }

    public String uploadToken(String bucket, String key) {
        return this.uploadToken(bucket, key, 3600L, null, true);
    }

    public String uploadToken(String bucket, String key, long expires, StringMap policy) {
        return this.uploadToken(bucket, key, expires, policy, true);
    }

    public String uploadToken(String bucket, String key, long expires, StringMap policy, boolean strict) {
        long deadline = System.currentTimeMillis() / 1000L + expires;
        return this.uploadTokenWithDeadline(bucket, key, deadline, policy, strict);
    }

    public String uploadTokenWithDeadline(String bucket, String key, long deadline, StringMap policy, boolean strict) {
        String scope = bucket;
        if(key != null) {
            scope = bucket + ":" + key;
        }

        StringMap x = new StringMap();
        copyPolicy(x, policy, strict);
        x.put("scope", scope);
        x.put("deadline", Long.valueOf(deadline));
        String s = Json.encode(x);
        return this.signWithData(StringUtils.utf8Bytes(s));
    }

    public StringMap authorization(String url, byte[] body, String contentType) {
        String authorization = "QBox " + this.signRequest(url, body, contentType);
        return (new StringMap()).put("Authorization", authorization);
    }

    public StringMap authorization(String url) {
        return this.authorization(url, null, null);
    }
}
