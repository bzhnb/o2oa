package com.x.message.assemble.communicate.jaxrs.consume;

import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.jaxrs.WrapBoolean;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.message.core.entity.Message;

class ActionUpdateSingle extends BaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActionUpdateSingle.class);

	ActionResult<Wo> execute(EffectivePerson effectivePerson, String id, String type) throws Exception {
		LOGGER.debug("execute:{}, id:{}, type:{}.", effectivePerson::getDistinguishedName, () -> id, () -> type);
		try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
			ActionResult<Wo> result = new ActionResult<>();
			Message message = emc.find(id, Message.class);
			if (null != message) {
				emc.beginTransaction(Message.class);
				message.setConsumed(true);
				emc.commit();
			}
			Wo wo = new Wo();
			wo.setValue(true);
			result.setData(wo);
			return result;
		}
	}

	public static class Wo extends WrapBoolean {

		private static final long serialVersionUID = -8396900361845254097L;

	}

}