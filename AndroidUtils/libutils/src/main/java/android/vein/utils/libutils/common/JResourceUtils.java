package android.vein.utils.libutils.common;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;

/**
 * Created by vein on 16/8/25.
 */
public class JResourceUtils {
    private Resources resources;
    private String pkg;
    private static Context context;
    private static JResourceUtils mcResource;
    private static Object lock = new Object();

    private JResourceUtils(Context context) {
        this.context = context;
        pkg = context.getPackageName();
        resources = context.getResources();
    }

    public static JResourceUtils getInstance(Context context) {
        synchronized (lock) {
            if (mcResource == null) {
                mcResource = new JResourceUtils(context.getApplicationContext());
            }
        }
        return mcResource;
    }

    public int getResourcesId(String type, String name) {
        try {
            int id = resources.getIdentifier(name, type, pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getStringId(String name) {
        try {
            int id = resources.getIdentifier(name, "string", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getString(String name) {
        String s = "";
        int id = getStringId(name);
        try {
            s = resources.getString(id);
        } catch (Exception e) {
            e.printStackTrace();
            s = "";
        }
        // MCLogUtil.i("MCResource", "s = " + s);
        return s;
    }

    public int getColorId(String name) {
        try {
            int id = resources.getIdentifier(name, "color", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getColor(String name) {
        int color = Color.TRANSPARENT;
        int id = getColorId(name);
        try {
            color = resources.getColor(id);
        } catch (Exception e) {
            color = 0;
        }
        return color;
    }

    public ColorStateList getColorStateList(String name) {
        ColorStateList colorStateList = null;
        try {
            colorStateList = resources.getColorStateList(getColorId(name));
        } catch (Exception e) {
            colorStateList = new ColorStateList(null, new int[] { Color.GRAY });
        }
        return colorStateList;
    }

    public int getDimenId(String name) {
        try {
            int id = resources.getIdentifier(name, "dimen", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getStyleId(String name) {
        try {
            int id = resources.getIdentifier(name, "style", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getLayoutId(String name) {
        try {
            int id = resources.getIdentifier(name, "layout", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getViewId(String name) {
        try {
            int id = resources.getIdentifier(name, "id", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getAnimId(String name) {
        try {
            int id = resources.getIdentifier(name, "anim", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getArrayId(String name) {
        try {
            int id = resources.getIdentifier(name, "array", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDrawableId(String name) {
        try {
            int id = resources.getIdentifier(name, "drawable", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Drawable getDrawable(String name) {
        Drawable drawable = null;
        try {
            int id = getDrawableId(name);
            drawable = resources.getDrawable(id);
        } catch (Exception e) {
            drawable = null;
        }
        return drawable;
    }

    public int getRawId(String name) {
        try {
            int id = resources.getIdentifier(name, "raw", pkg);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * 对于context.getResources().getIdentifier无法获取的数据,或者数组
     *
     * 资源反射值
     *
     * @paramcontext
     *
     * @param name
     *
     * @param type
     *
     * @return
     *
     */

    private Object getResourceId(String name, String type) {

        String className = context.getPackageName() + ".R";

        try {

            Class cls = Class.forName(className);

            for (Class childClass : cls.getClasses()) {

                String simple = childClass.getSimpleName();

                if (simple.equals(type)) {

                    for (Field field : childClass.getFields()) {

                        String fieldName = field.getName();

                        if (fieldName.equals(name)) {

                            System.out.println(fieldName);

                            return field.get(null);

                        }

                    }

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

    /**
     *
     * context.getResources().getIdentifier无法获取到styleable的数据
     *
     * @paramcontext
     *
     * @param name
     *
     * @return
     *
     */

    public int getStyleable(String name) {

        return ((Integer) getResourceId(name, "styleable")).intValue();

    }

    /**
     *
     * 获取styleable的ID号数组
     *
     * @paramcontext
     *
     * @param name
     *
     * @return
     *
     */

    public int[] getStyleableArray(String name) {

        return (int[]) getResourceId(name, "styleable");

    }

}
