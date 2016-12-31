package cn.nest.annotation.until;

import cn.nest.annotation.BeanTransfer;
import cn.nest.annotation.ColumTransfer;
import cn.nest.annotation.constants.TransferType;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by perk
 *
 * @DATE 2016/12/16
 * @DESC
 */
public class AnnotationUnitl {

    public static String bean2String(Object beanObject) {

        Class annotationClassz = beanObject.getClass();

        boolean isExist = annotationClassz.isAnnotationPresent(BeanTransfer.class);

        if (!isExist) {
            return null;
        }

        BeanTransfer beanTransfer = (BeanTransfer) annotationClassz.getAnnotation(BeanTransfer.class);
        Map<String, Object> params = new ConcurrentHashMap<>();
        params.clear();

        for (Field field : annotationClassz.getDeclaredFields()) {

            boolean isColumnExist = field.isAnnotationPresent(ColumTransfer.class);
            if (!isColumnExist) {
                return null;
            }

            ColumTransfer columnTransfer = field.getAnnotation(ColumTransfer.class);

            String columnName = columnTransfer.value();
            String fildName = field.getName();
            String getMethodName = "get" + fildName.substring(0, 1).toUpperCase() + fildName.substring(1);

            try {
                Method getMethod = annotationClassz.getMethod(getMethodName);
                if (!StringUtils.isEmpty(columnName)) {
                    params.put(columnName, getMethod.invoke(beanObject));
                } else {
                    params.put(fildName, getMethod.invoke(beanObject));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        String type = beanTransfer.type();

        if (TransferType.BEAN_TYPE_JSON.equals(type)) {
            return JSON.toJSONString(params);
        }

        return null;
    }
}
