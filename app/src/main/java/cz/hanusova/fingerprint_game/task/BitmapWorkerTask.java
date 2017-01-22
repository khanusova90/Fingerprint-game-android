package cz.hanusova.fingerprint_game.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Async task to save/load Bitmap to/from cache
 */

public class BitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {

    private String DIR_NAME = "testDir";
    private DiskLruCache diskLruCache;
    private String bitmapName;
    private File cacheDir;
    private Context context;
    private int appVersion;
    private String imageKey;

    /**
     * To pass various objects easily
     */
    public BitmapWorkerTask(String bitmapName, Context context, int appVersion) {
        this.bitmapName = bitmapName;
        this.context = context;
        this.appVersion = appVersion;


    }

    /**
     * Init cache dictionary and open it
     */
    @Override
    protected void onPreExecute() {
        // creates a new dir in internal storage
        imageKey = bitmapName.substring(0, bitmapName.lastIndexOf('.')).toLowerCase(); // whole name without
        cacheDir = context.getDir(DIR_NAME, Context.MODE_PRIVATE); //Creating an internal dir;
        try {
            diskLruCache = DiskLruCache.open(cacheDir, appVersion, 1, Integer.MAX_VALUE);
        } catch (IOException e) {
            //TODO: handle this
            e.printStackTrace();
        }
    }

    /**
     * Check if required Bitmap is in the cache, if not save it
     */
    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap bitmap = get(imageKey);
        if (bitmap == null) { // Not found in disk cache
            try {
                URL url = new URL(Constants.IMG_URL_BASE + bitmapName);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                put(imageKey, bitmap);
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return bitmap;
    }

    /**
     * Serialize bitmap to cache
     */
    private void put(String imageKey, Bitmap bitmap) throws IOException {
        DiskLruCache.Editor editor = null;
        try {
            editor = diskLruCache.edit(imageKey);
            if (editor != null) {
                OutputStream dataOs = editor.newOutputStream(0);
                OutputStream dataBos = new BufferedOutputStream(dataOs, 1024);
                ObjectOutputStream dataOos = new ObjectOutputStream(dataBos);
                dataOos.writeObject(bitmap.compress(Bitmap.CompressFormat.PNG, 100, dataOos));
                dataOos.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (editor != null)
                diskLruCache.flush();
                editor.commit();
        }
    }

    /**
     * Decode Bitmap from cache
     */
    private Bitmap get(String imageKey) {
        Bitmap bitmap = null;

        DiskLruCache.Snapshot snapshot;
        try {
            snapshot = diskLruCache.get(imageKey);
            if (snapshot == null) {
                return null;
            }
            ObjectInputStream in = new ObjectInputStream(snapshot.getInputStream(0));
            final BufferedInputStream buffIn = new BufferedInputStream(in, 1024);
            bitmap = BitmapFactory.decodeStream(buffIn);
            return bitmap;


        } catch (IOException e) {
            // TODO: handle this
            e.printStackTrace();
        }
        return bitmap;

    }
}
