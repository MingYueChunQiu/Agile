package com.mingyuechunqiu.agile.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mingyuechunqiu.agile.R;

import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/19
 *     desc   : 控制item显示高度，可删除记录的信息列表适配器
 *              继承自BaseAdapter
 *     version: 1.0
 * </pre>
 */
public class PopListLvAdapter extends BaseAdapter {

    private Context mContext;
    private List<Info> mList;
    private int mItemHeight;//每个item的高度
    private OnClickDeleteBtnListener mListener;

    public PopListLvAdapter(Context context, List<Info> list, int itemHeight) {
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
            viewHolder.tvUsername = convertView.findViewById(R.id.tv_pop_list_item_name);
            viewHolder.ibtnDelete = convertView.findViewById(R.id.ibtn_pop_list_delete);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickView(mList.get(position), position);
                    }
                }
            });
            viewHolder.ibtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClickDeleteBtn(mList.get(position), position);
                    }
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvUsername.setText(mList.get(position).getName());
        return convertView;
    }

    public OnClickDeleteBtnListener getOnClickDeleteBtnListener() {
        return mListener;
    }

    public void setOnClickDeleteBtnListener(OnClickDeleteBtnListener listener) {
        mListener = listener;
    }

    public List<Info> getListData() {
        return mList;
    }

    private static class ViewHolder {
        private AppCompatTextView tvUsername;
        private AppCompatImageButton ibtnDelete;
    }

    public static class Info {
        private String name;//名称
        private Bundle bundle;//绑定的信息

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Bundle getBundle() {
            return bundle;
        }

        public void setBundle(Bundle bundle) {
            this.bundle = bundle;
        }
    }

    /**
     * 删除按钮的点击监听器
     */
    public interface OnClickDeleteBtnListener {

        /**
         * 当点击item的view时回调
         *
         * @param Info     被点击的信息
         * @param position 被点击的索引位置
         */
        void onClickView(Info Info, int position);

        /**
         * 当点击删除按钮时回调
         *
         * @param Info     要的用户信息
         * @param position 被点击的索引位置
         */
        void onClickDeleteBtn(Info Info, int position);
    }
}
