package com.demo.mylistview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;

/**
 * Created by passion on 2017/7/25.
 */

public class DescriptionDragListView extends LinearLayout implements SlideAndDragListView.OnDragDropListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, SlideAndDragListView.OnMenuItemClickListener, SlideAndDragListView.OnItemDeleteListener, View.OnClickListener, PointDragListView.OnPointItemClickListener, PointDragListView.OnPointItemDeleteListener {

    private Menu mMenu;
    private Context mContext;
    private DescListAdapter mAdapter;
    private DescriptionBean mDraggedEntity;
    private ArrayList<DescriptionBean> mDescriptionList;
    private SlideAndDragListView mDescriptionDragListView;

    public DescriptionDragListView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public DescriptionDragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public DescriptionDragListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public void initMenu() {
        mMenu = new Menu(true);
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width) + 30)
                .setBackground(Utils.getDrawable(mContext, R.drawable.btn_right0))
                .setText("编辑")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.BLACK)
                .setTextSize(14)
                .build());
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width_img))
                .setBackground(Utils.getDrawable(mContext, R.drawable.btn_right1))
                .setText("删除")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setIcon(getResources().getDrawable(R.drawable.btn_right1))
                .build());
    }

    private void init() {
        View descView = LayoutInflater.from(mContext).inflate(R.layout.desc_drag_view_layout, null);
        addView(descView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mDescriptionDragListView = (SlideAndDragListView) descView.findViewById(R.id.desc_recycle_view);

        initMenu();

        View header = LayoutInflater.from(mContext).inflate(R.layout.point_item_head_view, null);
        header.findViewById(R.id.add_talk_words).setOnClickListener(this);
        mDescriptionDragListView.addHeaderView(header);
        mDescriptionDragListView.setMenu(mMenu);

        mDescriptionDragListView.setOnDragDropListener(this);
        mDescriptionDragListView.setOnItemClickListener(this);
        mDescriptionDragListView.setOnItemDeleteListener(this);
        mDescriptionDragListView.setOnMenuItemClickListener(this);
        mDescriptionDragListView.setOnItemLongClickListener(this);
    }


    public void notifyData(ArrayList<DescriptionBean> list) {
        mDescriptionList = list;
        if (mAdapter == null) {
            mAdapter = new DescListAdapter(mContext, mDescriptionList);
            mDescriptionDragListView.setAdapter(mAdapter);
        }
        mDescriptionDragListView.setAdapter(mAdapter);
    }


    @Override
    public void onDragViewStart(int beginPosition) {
        mDraggedEntity = mDescriptionList.get(beginPosition);
    }

    @Override
    public void onDragDropViewMoved(int fromPosition, int toPosition) {
        DescriptionBean bean = mDescriptionList.remove(fromPosition);
        mDescriptionList.add(toPosition, bean);
    }

    @Override
    public void onDragViewDown(int finalPosition) {
        mDraggedEntity.position = finalPosition;
        mDescriptionList.set(finalPosition, mDraggedEntity);
        for (int i = 0; i < mDescriptionList.size(); i++) {
            DescriptionBean bean = mDescriptionList.get(i);
            bean.position = i;
            DatabaseUtil.getInstance().modifyDescription(mContext, bean);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return true;
    }


    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        if (direction == MenuItem.DIRECTION_LEFT){
            if (buttonPosition == 0){
                return Menu.ITEM_NOTHING;
            }

            if (buttonPosition == 1){
                return Menu.ITEM_SCROLL_BACK;
            }
        }
        if (direction == MenuItem.DIRECTION_RIGHT){
            if (buttonPosition == 0){
                return Menu.ITEM_SCROLL_BACK;
            }
            if (buttonPosition == 1){
                return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
            }
        }

        return Menu.ITEM_NOTHING;

//        switch (direction) {
//            case MenuItem.DIRECTION_LEFT:
//                switch (buttonPosition) {
//                    case 0:
//                        return Menu.ITEM_NOTHING;
//                    case 1:
//                        return Menu.ITEM_SCROLL_BACK;
//                }
//                break;
//            case MenuItem.DIRECTION_RIGHT:
//                switch (buttonPosition) {
//                    case 0:
//                        return Menu.ITEM_SCROLL_BACK;
//                    case 1:
//                        return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
//                }
//        }
//        return Menu.ITEM_NOTHING;
    }

    @Override
    public void onItemDeleteAnimationFinished(View view, int position) {
        int deletePosition = position - mDescriptionDragListView.getHeaderViewsCount();

        DatabaseUtil.getInstance().deleteDescriptionByEntity(mContext, mDescriptionList.get(deletePosition));

        mDescriptionList.remove(deletePosition);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.add_talk_words) {
            Toast.makeText(mContext, "添加条目", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointItemClick(PointBean bean) {
        if (mDescriptionList == null){
            mDescriptionList = new ArrayList<>();
        }else{
            mDescriptionList.clear();
        }
        mDescriptionList.addAll(DatabaseUtil.getInstance().queryAllDescByPointId(mContext, bean.id));
        notifyData(mDescriptionList);
    }

    @Override
    public void onPointItemDelete(PointBean bean) {
        DatabaseUtil.getInstance().deleteDescriptionByKey(mContext,bean.id);

        mDescriptionList.clear();
        notifyData(mDescriptionList);
    }
}
