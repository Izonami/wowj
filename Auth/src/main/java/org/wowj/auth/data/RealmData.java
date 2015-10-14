package org.wowj.auth.data;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by kuksin-mv on 14.10.2015.
 */
@Entity
@Table(name = "realmlist")
public class RealmData
{
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME", length = 32, nullable = false)
    private String name;

    @Column(name = "ADDRESS", length = 32, nullable = false)
    private String address;

    @Column(name = "PORT")
    private Integer port;

    @Column(name = "ICON")
    private Integer icon;

    @Column(name = "REALMFLAGS")
    private Integer realmflags;

    @Column(name = "TIMEZONE")
    private Integer timezone;

    @Column(name = "ALLOWEDSECURITYLEVEL")
    private Integer allowedSecurityLevel;

    @Column(name = "POPULATION")
    private Float population;

    @Column(name = "REALMBUILDS", length = 64, nullable = false)
    private String realmbuilds;

    public Integer getId() {

        return this.id;
    }

    public void setId(final Integer id) {

        this.id = id;
    }

    public String getName() {

        return this.name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public String getAddress() {

        return this.address;
    }

    public void setAddress(final String address) {

        this.address = address;
    }

    public Integer getPort() {

        return this.port;
    }

    public void setPort(final Integer port) {

        this.port = port;
    }

    public Integer getIcon() {

        return this.icon;
    }

    public void setIcon(final Integer icon) {

        this.icon = icon;
    }

    public Integer getRealmflags() {

        return this.realmflags;
    }

    public void setRealmflags(final Integer realmflags) {

        this.realmflags = realmflags;
    }

    public Integer getTimezone() {

        return this.timezone;
    }

    public void setTimezone(final Integer timezone) {

        this.timezone = timezone;
    }

    public Integer getAllowedSecurityLevel() {

        return this.allowedSecurityLevel;
    }

    public void setAllowedSecurityLevel(final Integer allowedSecurityLevel) {

        this.allowedSecurityLevel = allowedSecurityLevel;
    }

    public Float getPopulation() {

        return this.population;
    }

    public void setPopulation(final Float population) {

        this.population = population;
    }

    public String getRealmbuilds() {

        return this.realmbuilds;
    }

    public void setRealmbuilds(final String realmbuilds) {

        this.realmbuilds = realmbuilds;
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id, this.name);
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
        final RealmData other = (RealmData) obj;
        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name);
    }
}
