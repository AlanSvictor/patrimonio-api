package br.com.navita.api.controllers;

import br.com.navita.api.dtos.DeviceGroupDto;
import br.com.navita.api.service.DeviceGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/deviceGroup")
@CrossOrigin(origins = "*")
public class DeviceGroupController {

    @Autowired
    private DeviceGroupService deviceGroupService;

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody DeviceGroupDto deviceGroupDto) {
        return ResponseEntity.ok(deviceGroupService.persist(deviceGroupDto.toEntity()));
    }

    @GetMapping(value = "/{deviceGroupName}")
    public ResponseEntity<List<?>> getDeviceGroup(@PathVariable(name = "deviceGroupName") String deviceGroupName) {
        return ResponseEntity.ok(deviceGroupService.findDeviceGroupByName(deviceGroupName));
    }

}
