package br.com.navita.api.dtos;

import br.com.navita.api.entities.Device;
import br.com.navita.api.entities.DeviceGroup;
import lombok.Data;

import java.util.List;

@Data
public class DeviceGroupDto {

    private String name;
    private List<Device> deviceList;

    public DeviceGroup toEntity() {
        return new DeviceGroup(name, deviceList);
    }
}
