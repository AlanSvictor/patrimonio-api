package br.com.navita.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "device_id")
    private List<DeviceHistory> deviceHistory;

    public Device (String name, List<DeviceHistory> deviceHistory){
        this.name = name;
        this.deviceHistory = deviceHistory;
    }
}
