package com.mingyuechunqiu.agilemvpframeproject.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mingyuechunqiu.agilemvpframeproject.R;
import com.mingyuechunqiu.agilemvpframeproject.data.database.realmDB.bean.User;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/19
 *     desc   : 控制item显示高度，可删除记录的用户账号列表适配器
 *              继承自BaseAdapter
 *     version: 1.0
 * </pre>
 */
public class PopListLvAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> mList;
    private int mItemHeight;//每个item的高度
    private OnClickDeleteBtnListener mListener;

    public PopListLvAdapter(Context context, List<User> list, int itemHeight) {
        mContext = context;
        mList = list;
        mItemHeight = itemHeight;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.agile_lv_pop_list_item, parent, false);
            convertView.getLayoutParams().height = mItemHeight;
            viewHolder = new ViewHolder();
            viewHolder.actvUsername = convertView.findViewById(R.id.tv_pop_list_item_name);
            viewHolder.acibtnDelete = convertView.findViewById(R.id.ibtn_pop_list_delete);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickView(mList.get(position), position);
                    }
                }
            });
            viewHolder.acibtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickDeleteBtn(mList.get(position));
                    }
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.actvUsername.setText(mList.get(position).getName());
        return convertView;
    }

    public OnClickDeleteBtnListener getOnClickDeleteBtnListener() {
        return mListener;
    }

    public void setOnClickDeleteBtnListener(OnClickDeleteBtnListener listener) {
        mListener = listener;
    }

    private static class ViewHolder {
        private AppCompatTextView actvUsername;
        private AppCompatImageButton acibtnDelete;
    }

    /**
     * 删除按钮的点击监听器
     */
    interface OnClickDeleteBtnListener {

        /**
         * 当点击item的view时回调
         *
         * @param user     被点击的用户信息
         * @param position 被点击的索引位置
         */
        void onClickView(User user, int position);

        /**
         * 当点击删除按钮时回调
         *
         * @param user 要删除的用户信息
         */
        void onClickDeleteBtn(User user);
    }
}
