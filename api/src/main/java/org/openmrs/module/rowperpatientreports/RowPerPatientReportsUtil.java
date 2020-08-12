package org.openmrs.module.rowperpatientreports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.DrugOrder;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;

/**
 * This is a new class, created during the upgrade from 1.9 to 2.3 data model, to encapsulate methods needed
 * in order to support this migration effort, either because they were removed from core, or because there are
 * adaptations needed.
 */
public class RowPerPatientReportsUtil {

	/**
	 * Needed due to removal of Context.getOrderService().getDrugOrdersByPatient(patient); method
	 * This may need to be improved for performance, etc. but for now should do the trick.
	 */
	public static List<DrugOrder> getDrugOrdersByPatient(Patient patient) {
		List<DrugOrder> l = new ArrayList<DrugOrder>();
		for (Order o : Context.getOrderService().getAllOrdersByPatient(patient)) {
			if (o instanceof DrugOrder) {
				DrugOrder drugOrder = (DrugOrder)o;
				if (!drugOrder.getVoided() && drugOrder.getAction() != Order.Action.DISCONTINUE ) {
					l.add((DrugOrder)o);
				}
			}
		}
		return l;
	}

	public static boolean isOrdered(Order drugOrder) {
		return !drugOrder.getVoided() && drugOrder.getAction() != Order.Action.DISCONTINUE;
	}

	public static boolean isPast(Order drugOrder) {
		return isPast(drugOrder, new Date());
	}

	public static boolean isPast(Order drugOrder, Date onDate) {
		return isOrdered(drugOrder) && (drugOrder.isDiscontinued(onDate) || drugOrder.isExpired(onDate));
	}

	public static boolean isCurrent(Order drugOrder) {
		return isOrdered(drugOrder) && !isPast(drugOrder) && drugOrder.isStarted();
	}

	public static boolean isCurrent(Order drugOrder, Date onDate) {
		return isOrdered(drugOrder) && !isPast(drugOrder, onDate) && drugOrder.isStarted(onDate);
	}

	public static boolean isFuture(Order drugOrder) {
		return isOrdered(drugOrder) && !isPast(drugOrder) && !drugOrder.isStarted();
	}
}
