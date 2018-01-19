package com.wizinno.apollo.common.data;

/**
 * Created by HP on 2017/11/10.
 */
public class TokenInfo {
    private String jti;

    private String iat;

    private String sub;
    private Long exp;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }


    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }
}
