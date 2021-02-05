package br.com.navita.api.repository;

import br.com.navita.api.entities.DeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {

    Optional<DeviceGroup> findById(Long id);

    List<DeviceGroup> findAllByName(String deviceGroupName);

}
