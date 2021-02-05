package br.com.navita.api.service.impl;

import br.com.navita.api.entities.DeviceGroup;
import br.com.navita.api.repository.DeviceGroupRepository;
import br.com.navita.api.service.DeviceGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceGroupImpl implements DeviceGroupService {

    @Autowired
    private DeviceGroupRepository deviceGroupRepository;

    @Override
    public DeviceGroup persist(DeviceGroup deviceGroup) {
        return deviceGroupRepository.save(deviceGroup);
    }

    @Override
    public List<DeviceGroup> findDeviceGroupByName(String deviceGroupName) {
        return deviceGroupRepository.findAllByName(deviceGroupName);
    }
}
