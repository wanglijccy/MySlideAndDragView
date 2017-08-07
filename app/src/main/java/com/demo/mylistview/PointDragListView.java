package com.demo.mylistview;

import android.content.Context;
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

public class PointDragListView extends LinearLayout implements SlideAndDragListView.OnDragDropListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, SlideAndDragListView.OnItemDeleteListener, SlideAndDragListView.OnMenuItemClickListener {

    private Menu mMenu;
    private Context mContext;
    private PointBean mDraggedEntity;
    private ArrayList<PointBean> mPointList;
    private SlideAndDragListView mPointDragListView;
    private PointListAdapter mAdapter;

    private Toast mToast;

    public PointDragListView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public PointDragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public PointDragListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public void initMenu() {
        mMenu = new Menu(true);
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width_img))
                .setBackground(Utils.getDrawable(mContext, R.drawable.btn_right1))
                .setText("删除")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setIcon(getResources().getDrawable(R.drawable.btn_right1))
                .build());
    }

    private void init() {
        View pointView = LayoutInflater.from(mContext).inflate(R.layout.point_drag_view_layout, null);
        addView(pointView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mPointDragListView = (SlideAndDragListView) pointView.findViewById(R.id.point_recycle_view);

        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);

        initMenu();

        mPointDragListView.setMenu(mMenu);

        mPointDragListView.setOnDragDropListener(this);
        mPointDragListView.setOnItemClickListener(this);
        mPointDragListView.setOnItemLongClickListener(this);
        mPointDragListView.setOnItemDeleteListener(this);
        mPointDragListView.setOnMenuItemClickListener(this);
    }


    public void notifyData(ArrayList<PointBean> list) {
        mPointList = list;
        if (mAdapter == null) {
            mAdapter = new PointListAdapter(mContext, mPointList);
            mPointDragListView.setAdapter(mAdapter);
        }
        mPointDragListView.setAdapter(mAdapter);
    }


    @Override
    public void onDragViewStart(int beginPosition) {
        mDraggedEntity = mPointList.get(beginPosition);
    }

    @Override
    public void onDragDropViewMoved(int fromPosition, int toPosition) {
        PointBean bean = mPointList.remove(fromPosition);
        mPointList.add(toPosition, bean);
    }

    @Override
    public void onDragViewDown(int finalPosition) {
        mDraggedEntity.position = finalPosition;
        mPointList.set(finalPosition, mDraggedEntity);
        for (int i = 0; i < mPointList.size(); i++) {
            PointBean bean = mPointList.get(i);
            bean.position = i;
            DatabaseUtil.getInstance().modifyPoint(mContext, bean);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mToast.setText("点所排的位置：" + mPointList.get(i).position);
        mToast.show();

        if (mPointItemClickListener != null) {
            mPointItemClickListener.onPointItemClick(mPointList.get(i));
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return true;
    }

    @Override
    public void onItemDeleteAnimationFinished(View view, int position) {
        int deletePosition = position - mPointDragListView.getHeaderViewsCount();

        //1：删除Description表中的数据
        if (mPointItemDeleteListener != null){
            mPointItemDeleteListener.onPointItemDelete(mPointList.get(deletePosition));
        }

        //2：删除Point 表中的数据
        DatabaseUtil.getInstance().deletePointByEntify(mContext, mPointList.get(deletePosition));

        //3: 更新Point列表UI
        mPointList.remove(deletePosition);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        if (direction == MenuItem.DIRECTION_LEFT){
            if(buttonPosition == 0){
                return Menu.ITEM_NOTHING;
            }
            if (buttonPosition == 1){
                return Menu.ITEM_SCROLL_BACK;
            }
        }

        if (direction == MenuItem.DIRECTION_RIGHT){
            if (buttonPosition == 0){
                return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
            }

            if (buttonPosition == 1){
                return Menu.ITEM_SCROLL_BACK;
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
//                        return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
//                    case 1:
//                        return Menu.ITEM_SCROLL_BACK;
//                }
//        }
//        return Menu.ITEM_NOTHING;
    }

    public interface OnPointItemClickListener {

        void onPointItemClick(PointBean bean);
    }

    public void setOnPointItemClickListener(OnPointItemClickListener listener) {
        this.mPointItemClickListener = listener;
    }

    private OnPointItemClickListener mPointItemClickListener;

    public interface OnPointItemDeleteListener {

        void onPointItemDelete(PointBean bean);
    }

    public void setOnPointItemDeleteListener(OnPointItemDeleteListener listener) {
        this.mPointItemDeleteListener = listener;
    }

    private OnPointItemDeleteListener mPointItemDeleteListener;
}
