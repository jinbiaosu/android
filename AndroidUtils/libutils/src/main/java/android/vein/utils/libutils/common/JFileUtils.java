package android.vein.utils.libutils.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by vein on 16/8/25.
 */
public class JFileUtils {

    /**
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    /**
     *
     * @param path
     * @return
     */
    public static boolean isDirExist(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    /**
     *
     * @param path
     * @return
     */
    public static boolean makeDirs(String path) {
        File file = new File(path);
        return file.mkdirs();
    }

    /**
     *
     * @param file
     * @return
     */
    public static int getFileSize(File file) {
        FileInputStream fis = null;
        int size = 0;
        try {
            fis = new FileInputStream(file);
            size = fis.available();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     *
     * @param activity
     * @param fileAbsolutePath
     * @param fileName
     * @return
     */
    public static boolean addToSysGallery(Activity activity, String fileAbsolutePath, String fileName) {
        try {
            if (activity == null)
                return false;
            MediaStore.Images.Media.insertImage(activity.getContentResolver(), fileAbsolutePath, fileName, fileName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @param context
     * @param fileAbsolutePath
     * @param fileName
     * @return
     */
    public static String addToSysGallery(Context context, String fileAbsolutePath, String fileName) {
        try {
            if (context == null)
                return null;
            File file = new File(fileAbsolutePath);
            if (!file.exists()) {
                return null;
            }
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), fileAbsolutePath, fileName, fileName);
            return path;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据Uri获取绝对路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePathByContentResolver(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        String filePath = null;
        if (null == c) {
            return null;
        }
        try {
            if ((c.getCount() != 1) || !c.moveToFirst()) {
            } else {
                filePath = c.getString(c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            }
        } finally {
            c.close();
        }
        return filePath;
    }

    /**
     * 发送添加系统相册成功通知
     *
     * @param context
     * @param absPath
     */
    public static void sendDownSucc(Context context, String absPath) {
        if (TextUtils.isEmpty(absPath)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(absPath));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public static String createFileDir(String path) {
        if (!isDirExist(path)) {
            boolean isMakeSucc = makeDirs(path);
            if (!isMakeSucc) {
                return null;
            }
        }
        return path;
    }
        /**
         * save log into file > sdcard
         * @param log
         */
    public static void saveLog(String log) {
        File file = new File(Environment.getExternalStorageDirectory() + "/appbyme.log");
        FileOutputStream fos = null;
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            fos.write(log.toString().getBytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存bitmap到文件
     *
     * @param filename
     * @param bmp
     * @return
     */
    public static String saveBitmapToSDCard(String filename, String foloderName, Bitmap bmp) {
        if (bmp == null) {
            return "";
        }

        // 文件相对路径
        String fileName = null;
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            // 文件保存的路径
            String fileDir = Environment.getExternalStorageDirectory() + foloderName;

            // 如果文件夹不存在，创建文件夹
            if (!createDir(fileDir)) {
                Log.e("", "创建文件夹失败!");
            }
            // 声明文件对象
            File file = null;
            // 声明输出流
            FileOutputStream outStream = null;

            try {
                // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
                file = new File(fileDir, filename);

                // 获得文件相对路径
                fileName = file.toString();
                // 获得输出流，如果文件中有内容，追加内容
                outStream = new FileOutputStream(fileName);
                if (outStream != null) {
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream);
                    outStream.close();
                }

            } catch (Exception e) {
                Log.e("", e.toString());
            } finally {
                // 关闭流
                try {
                    if (outStream != null) {
                        outStream.close();
                    }
                } catch (IOException e) {
                    Log.e("", e.toString());
                }
            }
        }
        return fileName;
    }

    /**
     * 创建指定路径的文件夹，并返回执行情况 ture or false
     *
     * @param filePath
     * @return
     */
    public static boolean createDir(String filePath) {
        File fileDir = new File(filePath); // 生成文件流对象
        boolean bRet = true;
        // 如果文件不存在，创建文件
        if (!fileDir.exists()) {
            // 获得文件或文件夹名称
            String[] aDirs = filePath.split("/");
            StringBuffer strDir = new StringBuffer();
            for (int i = 0; i < aDirs.length; i++) {
                // 获得文件上一级文件夹
                fileDir = new File(strDir.append("/").append(aDirs[i]).toString());
                // 是否存在
                if (!fileDir.exists()) {
                    // 不存在创建文件失败返回FALSE
                    if (!fileDir.mkdir()) {
                        bRet = false;
                        break;
                    }
                }
            }
        }

        return bRet;
    }

    public static boolean deleteFiles(String filePath) {
        if (!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return false;
        }
        String lastPath = Environment.getExternalStorageDirectory() + filePath;
        if (!new File(lastPath).exists()) {
            return false;
        }
        File file = new File(lastPath);
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }
        return file.delete();
    }

}
