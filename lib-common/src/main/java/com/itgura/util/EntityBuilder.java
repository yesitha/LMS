package com.itgura.util;

@SuppressWarnings("hiding")
public abstract class EntityBuilder <Request, Entity> {

	public final Entity buildCreateEntity(Request request, Entity entity)  {
		entity = initCreateEntity(request, entity);
		entity = initCreatedOrganization(entity);
        entity = initStatusBy(entity);
        entity = initStatusOrganization(entity);
        entity = initUpdatedOrganization(entity); 
		return initCreatedBy(entity);
	}

	public final Entity buildUpdateEntity(Request request, Entity entity) {
		entity = buildCreateEntity(request, entity);
		entity = initStatusBy(entity);
		entity = initStatusOrganization(entity);
		entity = initUpdatedOrganization(entity);
		return initUpdatedBy(entity);
	}
	
	public final Entity buildPatchEntity(Request request, Entity entity) {
		entity = initPatchEntity(request, entity);
		entity = initUpdatedOrganization(entity);
		return initUpdatedBy(entity);
	}

	protected abstract Entity initCreateEntity(Request request, Entity entity);

	protected Entity initUpdateEntity(Request request, Entity entity) {
		return entity;
	}
	protected Entity initPatchEntity(Request request, Entity entity) {
		return entity;
	}
	
	protected abstract Entity initCreatedBy(Entity entity);
	

	protected Entity initUpdatedBy(Entity entity) {
		return entity;
	}
	
	protected Entity initStatusBy(Entity entity) {
		return entity;
	}
	
	protected Entity initInactiveBy(Entity entity) {
		return entity;
	}
	
	protected Entity initStatusAt(Entity entity) {
		return entity;
	}
	
	protected Entity initCreatedOrganization(Entity entity) {
		return entity;
	}
	
	protected Entity initUpdatedOrganization(Entity entity) {
		return entity;
	}
	
	protected Entity initStatusOrganization(Entity entity) {
		return entity;
	}
	
	protected Entity initInactiveOrganization(Entity entity) {
		return entity;
	}
}
