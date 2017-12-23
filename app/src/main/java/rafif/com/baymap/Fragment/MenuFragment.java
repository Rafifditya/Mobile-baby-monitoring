package rafif.com.baymap.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rafif.com.baymap.Adapter.MenuAdapter;
import rafif.com.baymap.Model.MenuModel;
import rafif.com.baymap.R;
import rafif.com.baymap.Util.RecyclerTouchListener;

/**
 * Created by Nadia Widad on 12/4/2017.
 */

public class MenuFragment extends Fragment {

    private ArrayList<MenuModel> mMenu;
    private MenuAdapter mAdapterMenu;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private OnMenuSelectedListener mListener;


    public MenuFragment() {

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Inisialisasi Model dan load data set Main Category pertama kali
         */
        mMenu = new ArrayList<>();
        mMenu.add(new MenuModel(1, "Gizi Balita", "ic_gizi.png"));
        mMenu.add(new MenuModel(2, "Tumbuh Kembang Balita", "ic_baby.png"));
        mMenu.add(new MenuModel(3, "Jadwal Imunisasi", "ic_imunisasi.png"));
        mMenu.add(new MenuModel(4, "Jadwal Posyandu", "ic_posyandu.png"));
        mMenu.add(new MenuModel(5, "Catatan Kunjungan", "ic_catatan.png"));
        mMenu.add(new MenuModel(6, "Info Penting", "ic_info.png"));

        getMenuDataset(); //ini ada fungsi getMenuDataset() yang nyambungin ke BaseApiService, tapi aku ga paham ehe
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        Context mContext = getActivity().getApplicationContext();

        /**
         * Inisialisasi RecyclerView berserta adapter
         */
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_menu);
        mLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapterMenu = new MenuAdapter(mContext, mMenu);
        mRecyclerView.setAdapter(mAdapterMenu);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MenuModel modelMainCategory = mMenu.get(position);
                mListener.onMenuSelectedListener(modelMainCategory.getId());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return rootView;
    }

     public interface OnMenuSelectedListener {
        public void onMenuSelectedListener(int id);
    }
}
