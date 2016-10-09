package android.vein.utils.libutils.api;

/**
 * Created by vein on 16/9/12.
 */
public class HttpManager2 {
    private static  class HttpManager2Holder{
        private static final  HttpManager2 mInstance=new HttpManager2();
    }
    private HttpManager2(){

    }
    public static  HttpManager2 getInstance(){
        return HttpManager2Holder.mInstance;
    }
}
