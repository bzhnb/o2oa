package com.x.processplatform.assemble.surface.jaxrs.readcompleted;

import java.util.List;

import com.x.base.core.entity.JpaObject;
import com.x.base.core.project.annotation.FieldDescribe;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.jaxrs.EqualsTerms;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;
import com.x.processplatform.core.entity.content.ReadCompleted;

import io.swagger.v3.oas.annotations.media.Schema;

class ActionListPrev extends BaseAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActionListPrev.class);

	ActionResult<List<Wo>> execute(EffectivePerson effectivePerson, String id, Integer count) throws Exception {

		LOGGER.debug("execute:{}, id:{}, count:{}.", effectivePerson::getDistinguishedName, () -> id, () -> count);

		EqualsTerms equals = new EqualsTerms();
		equals.put("person", effectivePerson.getDistinguishedName());
		return this.standardListPrev(Wo.copier, id, count, JpaObject.sequence_FIELDNAME, equals, null, null, null, null,
				null, null, null, true, DESC);
	}

	@Schema(name = "com.x.processplatform.assemble.surface.jaxrs.readcompleted.ActionListPrev$Wo")
	public static class Wo extends ReadCompleted {

		private static final long serialVersionUID = 2279846765261247910L;

		static WrapCopier<ReadCompleted, Wo> copier = WrapCopierFactory.wo(ReadCompleted.class, Wo.class, null,
				JpaObject.FieldsInvisible);

		@FieldDescribe("排序号.")
		@Schema(description = "排序号.")
		private Long rank;

		public Long getRank() {
			return rank;
		}

		public void setRank(Long rank) {
			this.rank = rank;
		}

	}
}
