package br.com.navita.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "device_group")
public class DeviceGroup {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "device_group_id")
    private List<Device> device;

    public DeviceGroup (String name, List<Device> device){
        this.name = name;
        this.device = device;
    }

}
