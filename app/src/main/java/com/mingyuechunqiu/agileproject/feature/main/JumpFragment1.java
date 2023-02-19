package com.mingyuechunqiu.agileproject.feature.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher.ITransferPageDataDispatcher;
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.receiver.ITransferPageDataReceiver;
import com.mingyuechunqiu.agile.framework.ui.IFragmentInflateLayoutViewCreator;
import com.mingyuechunqiu.agile.ui.fragment.BaseFragment;
import com.mingyuechunqiu.agileproject.R;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/8/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class JumpFragment1 extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jump, container, false);
        LinearLayout llContainer = view.findViewById(R.id.ll_jump_container);
        llContainer.setBackgroundColor(Color.RED);
        Button btnJump = view.findViewById(R.id.btn_jump);
        btnJump.setOnClickListener(view1 -> getTransferPageDataDispatcherHelper().transferDataToActivity(null));
        return view;
    }

    @Nullable
    @Override
    protected IFragmentInflateLayoutViewCreator generateInflateLayoutViewCreator() {
        return new IFragmentInflateLayoutViewCreator.FragmentInflateLayoutViewCreatorAdapter() {

            @Override
            public int getInflateLayoutId() {
                return R.layout.fragment_jump;
            }
        };
    }

    @Override
    protected void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        getTransferPageDataReceiverHelper().addTransferDataReceiverListener(new ITransferPageDataReceiver.TransferPageDataReceiverListener() {
            @Override
            public void onReceiveTransferPageData(@NonNull ITransferPageDataDispatcher.TransferPageDataOwner dataOwner, @Nullable ITransferPageDataDispatcher.TransferPageData data) {
                if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                    getChildFragmentManager().popBackStack();
                }
            }
        });
    }

    @Override
    protected void releaseOnDestroyView() {

    }

    @Override
    protected void releaseOnDestroy() {

    }
}
