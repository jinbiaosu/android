//package android.vein.utils.libutils.view.pickerview.utils;
//
//import com.mobcent.discuz.application.DiscuzApplication;
//import com.mobcent.utils.DZResource;
//
//import android.view.Gravity;
//
///**
// * Created by Sai on 15/8/9.
// */
//public class PickerViewAnimateUtil {
//    private static final int INVALID = -1;
//    /**
//     * Get default animation resource when not defined by the user
//     *
//     * @param gravity       the gravity of the dialog
//     * @param isInAnimation determine if is in or out animation. true when is is
//     * @return the id of the animation resource
//     */
//    public static int getAnimationResource(int gravity, boolean isInAnimation) {
//    	DZResource resource = DZResource.getInstance(DiscuzApplication.getContext());
//        switch (gravity) {
//            case Gravity.BOTTOM:
//                return isInAnimation ? resource.getAnimId("slide_in_bottom") : resource.getAnimId("slide_out_bottom");
//        }
//        return INVALID;
//    }
//}
