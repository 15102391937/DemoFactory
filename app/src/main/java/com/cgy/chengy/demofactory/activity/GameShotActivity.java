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
            String fileDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "agame";
            File fileDirectory = new File(fileDirectoryPath);
            File[] list = fileDirectory.listFiles();
            for (int i = 0; i < list.length; i++) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap originalBitmap = BitmapFactory.decodeFile(list[i].getAbsolutePath(), options);
                Bitmap bitmap = Bitmap.createBitmap(originalBitmap, 44, 380, 452, 508);
                mList.add(bitmap);
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
}
