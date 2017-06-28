package pageParser.hibernate;

import javax.persistence.*;

/**
 * Created by Николай on 28.06.2017.
 */
@Entity
@Table(name = "APP")
public class AppartmentPO {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "rooms")
    private String rooms;
    @Column(name = "adress")
    private String adress;
    @Column(name = "taxi")
    private String taxi;


    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaxi() {
        return taxi;
    }

    public void setTaxi(String taxi) {
        this.taxi = taxi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppartmentPO that = (AppartmentPO) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", rooms='" + rooms + '\'' +
                ", adress='" + adress + '\'' +
                ", taxi='" + taxi + '\'' +
                "}\n";
    }
}
