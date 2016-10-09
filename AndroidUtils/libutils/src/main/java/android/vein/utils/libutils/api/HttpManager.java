package android.vein.utils.libutils.api;

/**
 * Created by vein on 16/9/12.
 */
public class HttpManager {
    private volatile  static HttpManager instance;
    private HttpManager(){

    }
    public static HttpManager getInstance(){
        if (instance==null){
            synchronized (HttpManager.class){
                if (instance==null){
                    instance=new HttpManager();
                }
            }
        }
        return  instance;
    }
}
