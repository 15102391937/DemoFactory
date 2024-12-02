package com.cgy.chengy.demofactory.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cgy.chengy.demofactory.R;
import com.cgy.chengy.demofactory.app.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameShotActivity extends BaseActivity {

    private RecyclerView rv;
    private Context mContext;
    private List<Bitmap> mList = new ArrayList<>();

    public static void start(Context context, int num) {
        Intent starter = new Intent(context, GameShotActivity.class);
        starter.putExtra("num", num);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_game_shot);
        rv = findViewById(R.id.rv);

        rv.setLayoutManager(new StaggeredGridLayoutManager(getIntent().getIntExtra("num", 7), StaggeredGridLayoutManager.VERTICAL));
        initList();
        rv.setAdapter(new GameShotAdapter());
    }

    private void initList() {
        try {
            String fileDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/agame";
            File fileDirectory = new File(fileDirectoryPath);
            File[] list = fileDirectory.listFiles();
            for (int i = 0; i < list.length; i++) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap originalBitmap = BitmapFactory.decodeFile(list[i].getAbsolutePath(), options);
                Bitmap bitmap = Bitmap.createBitmap(originalBitmap, 44, 380, 452, 508);
                mList.add(bitmap);
//                Bitmap bitmap = Bitmap.createBitmap(originalBitmap, 75, 185, 385, 480);
//                bitmap2File(bitmap, i + 1 + ".jpg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GameShotAdapter extends RecyclerView.Adapter<GameShotAdapter.ViewHolder> {

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_game_shot, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
            holder.iv.setImageBitmap(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView iv;

            ViewHolder(final View view) {
                super(view);
                iv = view.findViewById(R.id.iv);
            }
        }
    }

    private String bitmap2File(Bitmap bitmap, String fileName) {
        String shareDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/agame2/";
        createDir(new File(shareDirPath));
        File f = new File(shareDirPath + fileName);
        try {
            FileOutputStream fOut;
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            return null;
        }
        return f.getAbsolutePath();
    }

    private boolean createDir(File file) {
        if (file == null) return false;
        if (!file.exists() || !file.isDirectory()) {
            if (file.exists()) {
                try {
                    if (!delete(file)) return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                return file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean delete(final File file) {
        if (file == null) return false;
        if (file.isDirectory()) {
            return deleteDir(file);
        }
        return deleteFile(file);
    }

    private boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    private boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }
}
