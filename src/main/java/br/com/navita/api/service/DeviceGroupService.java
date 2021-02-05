package br.com.navita.api.service;

import br.com.navita.api.entities.DeviceGroup;

import java.util.List;

public interface DeviceGroupService {

    DeviceGroup persist(DeviceGroup deviceGroup);

    List<DeviceGroup> findDeviceGroupByName(String DeviceGroupName);
}
