package com.x.query.assemble.surface.jaxrs.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.entity.JpaObject;
import com.x.base.core.entity.annotation.CheckPersistType;
import com.x.base.core.entity.dynamic.DynamicEntity;
import com.x.base.core.project.exception.ExceptionAccessDenied;
import com.x.base.core.project.exception.ExceptionEntityNotExist;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.jaxrs.WrapBoolean;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.query.assemble.surface.Business;
import com.x.query.core.entity.schema.Table;

class ActionRowInsert extends BaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActionRowInsert.class);

	ActionResult<Wo> execute(EffectivePerson effectivePerson, String tableFlag, JsonElement jsonElement)
			throws Exception {
		LOGGER.debug("execute:{}, table:{}.", effectivePerson::getDistinguishedName, () -> tableFlag);
		ClassLoader classLoader = Business.getDynamicEntityClassLoader();
		Thread.currentThread().setContextClassLoader(classLoader);
		try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
			ActionResult<Wo> result = new ActionResult<>();
			Table table = emc.flag(tableFlag, Table.class);
			Business business = new Business(emc);
			if (null == table) {
				throw new ExceptionEntityNotExist(tableFlag, Table.class);
			}
			if (!business.editable(effectivePerson, table)) {
				throw new ExceptionAccessDenied(effectivePerson, table);
			}
			DynamicEntity dynamicEntity = new DynamicEntity(table.getName());
			@SuppressWarnings("unchecked")
			Class<? extends JpaObject> cls = (Class<? extends JpaObject>) classLoader
					.loadClass(dynamicEntity.className());
			List<Object> os = new ArrayList<>();

			if (jsonElement.isJsonArray()) {
				jsonElement.getAsJsonArray().forEach(o -> os.add(gson.fromJson(o, cls)));
			} else if (jsonElement.isJsonObject()) {
				os.add(gson.fromJson(jsonElement, cls));
			}
			emc.beginTransaction(cls);
			for (Object o : os) {
				emc.persist((JpaObject) o, CheckPersistType.all);
			}
			emc.commit();
			Wo wo = new Wo();
			wo.setValue(!os.isEmpty());
			result.setData(wo);
			return result;
		}
	}

	public static class Wo extends WrapBoolean {

		private static final long serialVersionUID = 314827464173768623L;

	}

}