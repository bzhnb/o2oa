package com.x.program.center.jaxrs.unexpectederrorlog;

import java.util.Date;
import java.util.List;

import com.x.base.core.entity.JpaObject;
import com.x.base.core.project.annotation.FieldDescribe;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.jaxrs.BetweenTerms;
import com.x.base.core.project.tools.DateTools;
import com.x.base.core.project.tools.ListTools;
import com.x.program.center.core.entity.UnexpectedErrorLog;

class ActionListPrevWithDate extends BaseAction {
	ActionResult<List<Wo>> execute(String id, Integer count, String date) throws Exception {
		Date dateTime = DateTools.parse(date);
		Date floor = DateTools.floorDate(dateTime, 0);
		Date ceil = DateTools.ceilDate(dateTime, 0);
		ActionResult<List<Wo>> result = new ActionResult<>();
		BetweenTerms betweens = new BetweenTerms();
		betweens.put(UnexpectedErrorLog.occurTime_FIELDNAME, ListTools.toList(floor, ceil));
		result = this.standardListPrev(Wo.copier, id, count, UnexpectedErrorLog.sequence_FIELDNAME, null, null, null,
				null, null, null, null, betweens, true, DESC);
		return result;
	}

	public static class Wo extends UnexpectedErrorLog {

		private static final long serialVersionUID = -7592184343034018992L;

		static WrapCopier<UnexpectedErrorLog, Wo> copier = WrapCopierFactory.wo(UnexpectedErrorLog.class, Wo.class,
				null, JpaObject.FieldsInvisible);

		@FieldDescribe("排序号")
		private Long rank;

		public Long getRank() {
			return rank;
		}

		public void setRank(Long rank) {
			this.rank = rank;
		}
	}
}
