package by.get.pms.model;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by milos on 24-Nov-16.
 */
@Embeddable
public class UserConnectionPK implements Serializable {

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "providerId", nullable = false)
    private String providerId;

    @Column(name = "providerUserId", nullable = false)
    private String providerUserId;

    public UserConnectionPK() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserConnectionPK)) return false;
        UserConnectionPK that = (UserConnectionPK) o;
        return Objects.equal(userId, that.userId) &&
                Objects.equal(providerId, that.providerId) &&
                Objects.equal(providerUserId, that.providerUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, providerId, providerUserId);
    }

    @Override
    public String toString() {
        return "UserConnectionPK{" +
                "userId='" + userId + '\'' +
                ", providerId='" + providerId + '\'' +
                ", providerUserId='" + providerUserId + '\'' +
                '}';
    }
}
