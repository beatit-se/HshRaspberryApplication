package se.beatit.hsh.raspberry.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by stefan on 12/31/17.
 */
@Entity
@Table(name="measuement")
public class Measurement  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fromDate;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date toDate;

    private Float inTemp;

    private Float outTemp;

    private Long wattHoursUsed;

    private Long avarageWatt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Float getInTemp() {
        return inTemp;
    }

    public void setInTemp(Float inTemp) {
        this.inTemp = inTemp;
    }

    public Float getOutTemp() {
        return outTemp;
    }

    public void setOutTemp(Float outTemp) {
        this.outTemp = outTemp;
    }

    public Long getWattHoursUsed() {
        return wattHoursUsed;
    }

    public void setWattHoursUsed(Long wattHoursUsed) {
        this.wattHoursUsed = wattHoursUsed;
    }

    public Long getAvarageWatt() {
        return avarageWatt;
    }

    public void setAvarageWatt(Long avarageWatt) {
        this.avarageWatt = avarageWatt;
    }
}
