package com.x.processplatform.service.processing.jaxrs.applicationdict;

import java.util.concurrent.Callable;

import com.google.gson.JsonElement;
import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.project.exception.ExceptionEntityNotExist;
import com.x.base.core.project.executor.ProcessPlatformExecutorFactory;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.jaxrs.WoId;
import com.x.processplatform.core.entity.element.ApplicationDict;
import com.x.processplatform.service.processing.Business;

class ActionUpdateDataPath7 extends BaseAction {

	ActionResult<Wo> execute(String id, String path0, String path1, String path2, String path3, String path4,
			String path5, String path6, String path7, JsonElement jsonElement) throws Exception {

		Callable<ActionResult<Wo>> callable = new Callable<ActionResult<Wo>>() {
			public ActionResult<Wo> call() throws Exception {
				try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
					ActionResult<Wo> result = new ActionResult<>();
					Business business = new Business(emc);
					ApplicationDict dict = emc.find(id, ApplicationDict.class);
					if (null == dict) {
						throw new ExceptionEntityNotExist(id, ApplicationDict.class);
					}
					update(business, dict, jsonElement, path0, path1, path2, path3, path4, path5, path6, path7);
					emc.commit();
					Wo wo = new Wo();
					wo.setId(dict.getId());
					result.setData(wo);
					return result;
				}
			}
		};

		return ProcessPlatformExecutorFactory.get(id).submit(callable).get();

	}

	public static class Wo extends WoId {

	}
}