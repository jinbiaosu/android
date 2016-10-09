package android.vein.utils.libutils.common;

import android.util.SparseArray;

import java.util.List;

/**
 * Created by vein on 16/8/25.
 */
public class JListUtils {
    /**
     *
     * @param data
     * @return ture is null,false is not null
     */
    public static boolean isEmpty(List<?> data) {
        if (data == null || data.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     *judge hashMap,SparseArray
     * @param data
     * @return ture is null,false is not null
     */
    public static boolean isEmpty(SparseArray<?> data) {
        if (data == null || data.size() == 0) {
            return true;
        }
        return false;
    }
}
