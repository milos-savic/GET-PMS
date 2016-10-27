package by.get.pms.dto;

/**
 * Created by Milos.Savic on 10/27/2016.
 */
public class EntitlementDTO extends DTO {

	private Long objectid;
	private ObjectType objectType;
	private Long userAccountId;
	private boolean permitted;

	public EntitlementDTO() {
		super();
	}

	public EntitlementDTO(Long objectid, ObjectType objectType, Long userAccountId, boolean permitted) {
		this.objectid = objectid;
		this.objectType = objectType;
		this.userAccountId = userAccountId;
		this.permitted = permitted;
	}

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public ObjectType getObjectType() {
		return objectType;
	}

	public void setObjectType(ObjectType objectType) {
		this.objectType = objectType;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	public boolean isPermitted() {
		return permitted;
	}

	public void setPermitted(boolean permitted) {
		this.permitted = permitted;
	}
}
