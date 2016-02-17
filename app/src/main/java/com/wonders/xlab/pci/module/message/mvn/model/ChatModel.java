package com.wonders.xlab.pci.module.message.mvn.model;

import android.content.Context;
import android.support.annotation.NonNull;

import im.hua.utils.NetworkUtil;
import com.wonders.xlab.pci.R;
import com.wonders.xlab.pci.module.base.mvn.entity.home.ChatEntity;
import com.wonders.xlab.pci.module.base.mvn.model.BaseModel;
import com.wonders.xlab.pci.module.message.bean.ChatBean;
import com.wonders.xlab.pci.module.message.bean.MessageBean;
import com.wonders.xlab.pci.module.message.mvn.api.ChatAPI;
import com.wonders.xlab.pci.module.message.mvn.view.ChatView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hua on 15/12/14.
 */
public class ChatModel extends BaseModel<ChatEntity> {
    private ChatView mChatView;
    private ChatAPI mChatAPI;
    private int page;
    private boolean isLastPage = false;

    public ChatModel(ChatView chatView) {
        mChatView = chatView;
        mChatAPI = mRetrofit.create(ChatAPI.class);
        page = -1;
    }

    /**
     * @param context
     * @param userId
     */
    public void getChatList(final Context context, final String userId) {

        /*if (page == -1) {
            Observable.just(ChatRealmEntity.class)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<Class<ChatRealmEntity>, RealmResults<ChatRealmEntity>>() {
                        @Override
                        public RealmResults<ChatRealmEntity> call(Class<ChatRealmEntity> messageEntity) {
                            Realm realm = Realm.getInstance(context);

                            return realm.where(ChatRealmEntity.class).equalTo("isMessage", true).findAll();
                        }
                    })
                    .filter(new Func1<RealmResults<ChatRealmEntity>, Boolean>() {
                        @Override
                        public Boolean call(RealmResults<ChatRealmEntity> historyTaskBeen) {
                            return historyTaskBeen != null && historyTaskBeen.size() > 0;
                        }
                    })
                    .flatMap(new Func1<RealmResults<ChatRealmEntity>, Observable<ChatRealmEntity>>() {
                        @Override
                        public Observable<ChatRealmEntity> call(RealmResults<ChatRealmEntity> chatRealmEntities) {
                            return Observable.from(chatRealmEntities);
                        }
                    })
                    .map(new Func1<ChatRealmEntity, MessageBean>() {
                        @Override
                        public MessageBean call(ChatRealmEntity chatRealmEntity) {
                            MessageBean messageBean = new MessageBean();
                            messageBean.setChatRealmEntity(chatRealmEntity);
                            return messageBean;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<MessageBean>() {
                        ArrayList<ChatBean> beanList = new ArrayList<>();

                        @Override
                        public void onCompleted() {
                            mChatView.showHomeList(beanList);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(MessageBean messageBean) {
                            beanList.add(messageBean);
                        }
                    });
        }*/

        if (!NetworkUtil.hasNetwork(context)) {
            mChatView.showError(context.getString(R.string.error_no_network));
            return;
        }
        if (!isLastPage) {
            setObservable(mChatAPI.getChatList(userId, page + 1));
        }

    }

    @Override
    protected void onSuccess(@NonNull ChatEntity response) {
        mChatView.hideLoading();
        ChatEntity.RetValuesEntity valuesEntity = response.getRet_values();

        if (valuesEntity == null) {
            mChatView.showError("获取数据失败，请重试！");
            return;
        }

        page = valuesEntity.getNumber();
        isLastPage = valuesEntity.isLast();

        List<ChatEntity.RetValuesEntity.ContentEntity> content = valuesEntity.getContent();
        if (content == null) {
            mChatView.showError("获取数据失败，请重试！");
            return;
        }

        List<ChatBean> beanList = new ArrayList<>();

        for (ChatEntity.RetValuesEntity.ContentEntity contentEntity : content) {

            MessageBean messageBean = new MessageBean();
            messageBean.setContent(contentEntity.getContent());
            messageBean.setTitle(contentEntity.getTitle());
            messageBean.setUpdateTime(contentEntity.getRecordTime());
            messageBean.setName(contentEntity.getName());
            messageBean.setPortrait(contentEntity.getPortrait());
            beanList.add(messageBean);

        }

        if (page > 0) {
            mChatView.appendHomeList(beanList);
        } else {
            mChatView.showHomeList(beanList);
        }
    }

    @Override
    protected void onFailed(String message) {
        mChatView.hideLoading();
        mChatView.showError(message);
    }

    @Override
    public synchronized void cancel() {
        super.cancel();
        page = -1;
        isLastPage = false;
    }
}
