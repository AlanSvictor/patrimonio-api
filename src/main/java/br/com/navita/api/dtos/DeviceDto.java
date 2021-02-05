package br.com.navita.api.dtos;

import br.com.navita.api.entities.Device;
import br.com.navita.api.entities.DeviceHistory;
import lombok.Data;

import java.util.List;

@Data
public class DeviceDto {

    private String name;
    private List<DeviceHistory> deviceHistoryList;


    public Device toEntity() {
        return new Device(name, deviceHistoryList);
    }
}
