package org.wowj.auth.data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
@Entity
@Table(name = "account")
public class AccountData
{
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "USERNAME", length = 32, unique = true)
    private String username;

    @Column(name = "SHA_PASS_HASH", length = 32)
    private String shaPasswordHash;

    @Column(name = "GMLEVEL")
    private Byte gmlevel;

    @Column(name = "SESSIONKEY")
    private String sessionKey;

    @Column(name = "V", nullable = false)
    private String v;

    @Column(name = "S", nullable = false)
    private String s;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "JOINDATE")
    private Calendar joindate;

    @Column(name = "LAST_IP", length = 30)
    private String lastIp;

    @Column(name = "FAILED_LOGINS")
    private Long failedLogins;

    @Column(name = "LOCKED")
    private Byte locked;

    @Column(name = "LAST_LOGIN")
    private Calendar lastLogin;

    @Column(name = "ACTIVE_REALM_ID")
    private Long activeRealmId;

    @Column(name = "EXPANSION")
    private Byte expansion;

    @Column(name = "MUTETIME")
    private Long mutetime;

    @Column(name = "LOCALE")
    private Byte locale;

    @Column(name = "LAST_SERVER")
    private Byte lastServer;

    public Long getId() {

        return this.id;
    }

    public void setId(final Long id) {

        this.id = id;
    }

    public String getUsername() {

        return this.username;
    }

    public void setUsername(final String username) {

        this.username = username;
    }

    public String getShaPasswordHash() {

        return this.shaPasswordHash;
    }

    public void setShaPasswordHash(final String shaPasswordHash) {

        this.shaPasswordHash = shaPasswordHash;
    }

    public Byte getGmlevel() {

        return this.gmlevel;
    }

    public void setGmlevel(final Byte gmlevel) {

        this.gmlevel = gmlevel;
    }

    public String getSessionKey() {

        return this.sessionKey;
    }

    public void setSessionKey(final String sessionKey) {

        this.sessionKey = sessionKey;
    }

    public String getV() {

        return this.v;
    }

    public void setV(final String v) {

        this.v = v;
    }

    public String getS() {

        return this.s;
    }

    public void setS(final String s) {

        this.s = s;
    }

    public String getEmail() {

        return this.email;
    }

    public void setEmail(final String email) {

        this.email = email;
    }

    public Calendar getJoindate() {

        return this.joindate;
    }

    public void setJoindate(final Calendar joindate) {

        this.joindate = joindate;
    }

    public String getLastIp() {

        return this.lastIp;
    }

    public void setLastIp(final String lastIp) {

        this.lastIp = lastIp;
    }

    public Long getFailedLogins() {

        return this.failedLogins;
    }

    public void setFailedLogins(final Long failedLogins) {

        this.failedLogins = failedLogins;
    }

    public Byte getLocked() {

        return this.locked;
    }

    public void setLocked(final Byte locked) {

        this.locked = locked;
    }

    public Calendar getLastLogin() {

        return this.lastLogin;
    }

    public void setLastLogin(final Calendar lastLogin) {

        this.lastLogin = lastLogin;
    }

    public Long getActiveRealmId() {

        return this.activeRealmId;
    }

    public void setActiveRealmId(final Long activeRealmId) {

        this.activeRealmId = activeRealmId;
    }

    public Byte getExpansion() {

        return this.expansion;
    }

    public void setExpansion(final Byte expansion) {

        this.expansion = expansion;
    }

    public Long getMutetime() {

        return this.mutetime;
    }

    public void setMutetime(final Long mutetime) {

        this.mutetime = mutetime;
    }

    public Byte getLocale() {

        return this.locale;
    }

    public void setLocale(final Byte locale) {

        this.locale = locale;
    }

    public Byte getLastServer() {

        return this.lastServer;
    }

    public void setLastServer(final Byte lastServer) {

        this.lastServer = lastServer;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id, this.username, this.shaPasswordHash);
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountData other = (AccountData) obj;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.username, other.username) &&
                Objects.equals(this.shaPasswordHash, other.shaPasswordHash);
    }
}
