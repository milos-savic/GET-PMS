package by.get.pms.utility;

import by.get.pms.dtos.TaskDTO;
import by.get.pms.dtos.TaskUpdateParamsForDev;
import by.get.pms.dtos.TaskUpdateParamsForPM;
import com.google.common.collect.Sets;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/25/2016.
 */
public final class TaskUtilities {

	public static Set<String> taskPropertiesAllowedForChangeByPM() {
		TaskUpdateParamsForPM taskUpdateParamsForPM = new TaskUpdateParamsForPM();
		BeanMap taskParamsForPMMap = new BeanMap(taskUpdateParamsForPM);
		Set<Object> taskParamsForPMPropNames = taskParamsForPMMap.keySet();

		return taskParamsForPMPropNames.parallelStream().map(o -> (String) o).collect(Collectors.toSet());
	}

	public static Set<String> taskPropertiesAllowedForChangeByDev() {
		TaskUpdateParamsForDev taskUpdateParamsForDev = new TaskUpdateParamsForDev();
		BeanMap taskParamsForDevMap = new BeanMap(taskUpdateParamsForDev);
		Set<Object> taskParamsForDevPropNames = taskParamsForDevMap.keySet();

		return taskParamsForDevPropNames.parallelStream().map(o -> (String) o).collect(Collectors.toSet());
	}

	public static Set<String> taskPropertiesWithDifferentValues(TaskDTO taskDTO1, TaskDTO taskDTO2)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Set<String> propertyNamesWithDiffValues = Sets.newHashSet();

		BeanMap map = new BeanMap(taskDTO1);
		PropertyUtilsBean propUtils = new PropertyUtilsBean();

		for (Object propNameObject : map.keySet()) {
			String propName = (String) propNameObject;
			Object propVal1 = propUtils.getProperty(taskDTO1, propName);
			Object propVal2 = propUtils.getProperty(taskDTO2, propName);
			if (!propVal1.equals(propVal2)) {
				propertyNamesWithDiffValues.add(propName);
			}
		}

		return propertyNamesWithDiffValues;
	}
}
