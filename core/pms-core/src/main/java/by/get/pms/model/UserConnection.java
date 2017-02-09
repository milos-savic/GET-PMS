package by.get.pms.model;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by milos on 24-Nov-16.
 */
@Entity
@Table(name = "UserConnection", uniqueConstraints = {
        @UniqueConstraint(name = "UserConnectionRank", columnNames = { "userId", "providerId", "rank" }) })
public class UserConnection implements Serializable {

    @EmbeddedId
    private UserConnectionPK pk;

    @Column(name = "rank", nullable = false)
    private int rank;

    @Column(name = "displayName")
    private String displayName;


    @Column(name = "profileUrl", length = 512)
    private String profileUrl;

    @Column(name = "imageUrl", length = 512)
    private String imageUrl;


    @Column(name = "accessToken", length = 1024, nullable = false)
    private String accessToken;

    @Column(name = "secret")
    private String secret;

    @Column(name = "refreshToken")
    private String refreshToken;

    public UserConnection() {
    }

    public UserConnectionPK getPk() {
        return pk;
    }

    public void setPk(UserConnectionPK pk) {
        this.pk = pk;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public BigInteger getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(BigInteger expireTime) {
        this.expireTime = expireTime;
    }

    @Column(name = "expireTime")
    private BigInteger expireTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserConnection)) return false;
        UserConnection that = (UserConnection) o;
        return Objects.equal(pk, that.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pk);
    }

    @Override
    public String toString() {
        return "UserConnection{" +
                "pk=" + pk +
                '}';
    }
}
