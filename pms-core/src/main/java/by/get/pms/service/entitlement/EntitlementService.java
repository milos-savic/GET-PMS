package by.get.pms.service.entitlement;

import by.get.pms.dto.EntitlementDTO;
import by.get.pms.dto.ObjectType;

import java.util.List;

/**
 * Created by Milos.Savic on 10/27/2016.
 */
public interface EntitlementService {

	List<EntitlementDTO> getEntitlementsForObjectTypePermittedToUser(String userName, ObjectType objectType);

}
