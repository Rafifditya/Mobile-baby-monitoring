package rafif.com.baymap.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rafif.com.baymap.Model.MenuModel;
import rafif.com.baymap.R;

/**
 * Created by Nadia Widad on 12/4/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context mContext;
    private ArrayList<MenuModel> mMenu;

    public MenuAdapter(Context context, ArrayList<MenuModel> menu) {
        this.mContext = context;
        this.mMenu = menu;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        MenuModel modelMenu = mMenu.get(position);
        String name = modelMenu.getName();
        holder.mTvMenuName.setText(name);
        String icon = modelMenu.getIcon();
        GlideApp.with(mContext).load(Uri.parse("file:///android_asset/" + icon)).into(holder.mIvMenuIcon);
    }


    @Override
    public int getItemCount() {
        return mMenu.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvMenuIcon;
        private TextView mTvMenuName;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mIvMenuIcon = (ImageView) itemView.findViewById(R.id.image_menu_icon);
            mTvMenuName = (TextView) itemView.findViewById(R.id.text_menu_name);
        }
    }
}
