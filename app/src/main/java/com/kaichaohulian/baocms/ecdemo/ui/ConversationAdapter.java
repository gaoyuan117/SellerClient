/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.kaichaohulian.baocms.ecdemo.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.constant.SystemPush;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.DateUtil;
import com.kaichaohulian.baocms.ecdemo.common.utils.DemoUtils;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferenceSettings;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferences;
import com.kaichaohulian.baocms.ecdemo.common.utils.ResourceHelper;
import com.kaichaohulian.baocms.ecdemo.storage.ContactSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.ConversationSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.GroupMemberSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.GroupNoticeSqlManager;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.base.EmojiconTextView;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.model.Conversation;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ContactLogic;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ECContacts;
import com.kaichaohulian.baocms.ecdemo.ui.group.GroupNoticeHelper;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.yuntongxun.ecsdk.ECMessage;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * @author 容联•云通讯
 * @version 4.0
 * @date 2014-12-8
 */
public class ConversationAdapter extends CCPListAdapter<Conversation> {
    private OnListAdapterCallBackListener mCallBackListener;
    int padding;
    private ColorStateList[] colorStateLists;
    private String isAtSession;
    private Context context;

    /**
     * @param ctx
     */
    public ConversationAdapter(Context ctx, OnListAdapterCallBackListener listener) {
        super(ctx, new Conversation());
        context = ctx;
        mCallBackListener = listener;
        padding = ctx.getResources().getDimensionPixelSize(R.dimen.OneDPPadding);
        colorStateLists = new ColorStateList[]{
                ResourceHelper.getColorStateList(ctx, R.color.normal_text_color),
                ResourceHelper.getColorStateList(ctx, R.color.ccp_list_textcolor_three)
        };
    }


    @Override
    protected Conversation getItem(Conversation t, Cursor cursor) {
        Conversation conversation = new Conversation();
        conversation.setCursor(cursor);
        if (conversation.getUsername() != null && conversation.getUsername().endsWith("@priategroup.com")) {
            ArrayList<String> member = GroupMemberSqlManager.getGroupMemberID(conversation.getSessionId());
            if (member != null) {
                ArrayList<String> contactName = ContactSqlManager.getContactName(member.toArray(new String[]{}));
                if (contactName != null && !contactName.isEmpty()) {
                    String chatroomName = DemoUtils.listToString(contactName, ",");
                    conversation.setUsername(chatroomName);
                }
            }
        }
        return conversation;
    }

    /**
     * 会话时间
     *
     * @param conversation
     * @return
     */
    protected final CharSequence getConversationTime(Conversation conversation) {
        if (conversation.getSendStatus() == ECMessage.MessageStatus.SENDING.ordinal()) {
            return mContext.getString(R.string.conv_msg_sending);
        }
        if (conversation.getDateTime() <= 0) {
            return "";
        }
        return DateUtil.getDateString(conversation.getDateTime(), DateUtil.SHOW_TYPE_CALL_LOG).trim();
    }

    /**
     * 根据消息类型返回相应的主题描述
     *
     * @param conversation
     * @return
     */
    protected final CharSequence getConversationSnippet(Conversation conversation) {
        if (conversation == null) {
            return "";
        }
        if (GroupNoticeSqlManager.CONTACT_ID.equals(conversation.getSessionId())) {
            return GroupNoticeHelper.getNoticeContent(conversation.getContent());
        }

        String fromNickName = "";
        if (conversation.getSessionId().toUpperCase().startsWith("G")) {
            if (conversation.getContactId() != null && CCPAppManager.getClientUser() != null
                    && !conversation.getContactId().equals(CCPAppManager.getClientUser().getUserId())) {
                ECContacts contact = ContactSqlManager.getContact(conversation.getContactId());

                if (contact != null && contact.getNickname() != null /*&& contact.getNickname().length() != 11*/) {
                    fromNickName = contact.getNickname() + ": ";
                } else {
                    fromNickName = "未填写" + ": ";
                }
                fromNickName="";
            }
        }/**/

        // Android Demo 免打扰后需要显示未读条数
        if (!conversation.isNotice() && conversation.getUnreadCount() > 1) {
            fromNickName = "[" + conversation.getUnreadCount() + "条]" + fromNickName;
        }
        int msgType = conversation.getMsgType();
        String content = conversation.getContent();
        Log.e("TRACE", "current content is : " + content);

        if (conversation.getMsgType() == ECMessage.Type.VOICE.ordinal()) {
            return fromNickName + mContext.getString(R.string.app_voice);
        } else if (conversation.getMsgType() == ECMessage.Type.FILE.ordinal()) {
            return fromNickName + mContext.getString(R.string.app_file);
        } else if (conversation.getMsgType() == ECMessage.Type.IMAGE.ordinal()) {
            return fromNickName + mContext.getString(R.string.app_pic);
        } else if (conversation.getMsgType() == ECMessage.Type.VIDEO.ordinal()) {
            return fromNickName + mContext.getString(R.string.app_video);
        } else if (conversation.getMsgType() == ECMessage.Type.LOCATION.ordinal()) {
            return fromNickName + mContext.getString(R.string.app_location);

        } else if (conversation.getMsgType() == ECMessage.Type.TXT.ordinal() && conversation.getContent() != null &&
                conversation.getContent().contains("cardtype")) {
            return fromNickName + "[个人名片]";
        } else if (conversation.getMsgType() == ECMessage.Type.TXT.ordinal() && conversation.getContent() != null &&
                conversation.getContent().contains("\"txt_msgType\":\"transfetype\"")) {
            return fromNickName + "[转账]";
        } else if (conversation.getMsgType() == ECMessage.Type.TXT.ordinal() && conversation.getContent() != null &&
                conversation.getContent().contains("zhuantyp2e")) {
            return fromNickName + "[已收钱]";
        }
        String snippet = fromNickName + conversation.getContent();
        if (conversation.getSessionId().equals(isAtSession)) {
            return Html.fromHtml(mContext.getString(R.string.conversation_at, snippet));
        }
        return snippet;
    }

    /**
     * 根据消息发送状态处理
     *
     * @param context
     * @param conversation
     * @return
     */
    public static Drawable getChattingSnippentCompoundDrawables(Context context, Conversation conversation) {
        if (conversation.getSendStatus() == ECMessage.MessageStatus.FAILED.ordinal()) {
            return DemoUtils.getDrawables(context, R.mipmap.msg_state_failed);
        } else if (conversation.getSendStatus() == ECMessage.MessageStatus.SENDING.ordinal()) {
            return DemoUtils.getDrawables(context, R.mipmap.msg_state_sending);
        } else {
            return null;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ViewHolder mViewHolder;
        if (convertView == null || convertView.getTag() == null) {
            view = View.inflate(mContext, R.layout.conversation_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.user_avatar = (ImageView) view.findViewById(R.id.avatar_iv);
            mViewHolder.prospect_iv = (ImageView) view.findViewById(R.id.avatar_prospect_iv);
            mViewHolder.nickname_tv = (EmojiconTextView) view.findViewById(R.id.nickname_tv);
            mViewHolder.tipcnt_tv = (TextView) view.findViewById(R.id.tipcnt_tv);
            mViewHolder.update_time_tv = (TextView) view.findViewById(R.id.update_time_tv);
            mViewHolder.last_msg_tv = (EmojiconTextView) view.findViewById(R.id.last_msg_tv);
            mViewHolder.image_input_text = (ImageView) view.findViewById(R.id.image_input_text);
            mViewHolder.image_mute = (ImageView) view.findViewById(R.id.image_mute);
            mViewHolder.avater_container = (RelativeLayout) view.findViewById(R.id.avater_container);
            view.setTag(mViewHolder);
        } else {
            view = convertView;
            mViewHolder = (ViewHolder) view.getTag();
        }

        Conversation conversation = getItem(position);
        if (conversation != null) {
            String info = SharedPrefsUtil.getValue(context, conversation.getSessionId(), null);
            String avaterPath = "";
            String nick = "";


            if (info != null) {
                Log.e("gy", "历史信息：" + info.toString());
                String[] data = info.split("-x-");
                Log.e("led---", info);
                if (data.length > 1) {
                    avaterPath = data[0];
                    nick = data[1];
                }
            }


            String s = MyApplication.getInstance().contactMap.get(conversation.getSessionId());
            if (!TextUtils.isEmpty(s) && !s.equals("null")) {
                mViewHolder.nickname_tv.setText(s);

            } else {
                if (TextUtils.isEmpty(nick)) {
                    mViewHolder.nickname_tv.setText(conversation.getSessionId());
                } else {
                    mViewHolder.nickname_tv.setText(nick);
                }
            }


            handleDisplayNameTextColor(mViewHolder.nickname_tv, conversation.getSessionId());
            mViewHolder.last_msg_tv.setText(getConversationSnippet(conversation));
            mViewHolder.last_msg_tv.setCompoundDrawables(getChattingSnippentCompoundDrawables(mContext, conversation), null, null, null);
            // 未读提醒设置
            setConversationUnread(mViewHolder, conversation);
            mViewHolder.image_input_text.setVisibility(View.GONE);
            mViewHolder.update_time_tv.setText(getConversationTime(conversation));

            mViewHolder.avater_container.removeAllViews();
            mViewHolder.user_avatar.setVisibility(View.VISIBLE);
            if (conversation.getSessionId().toUpperCase().startsWith("G")) {
                mViewHolder.user_avatar.setVisibility(View.GONE);

                ArrayList<String> avatar = new ArrayList<>();
                try {
                    org.json.JSONArray jsonArray = new org.json.JSONArray(avaterPath);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        avatar.add(jsonArray.get(i).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                View avaterview;
                mViewHolder.user_avatar.setVisibility(View.GONE);
                switch (avatar.size()) {
                    case 1:
                        mViewHolder.user_avatar.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.user_avatar);
                        break;
                    case 2:

                        avaterview = View.inflate(mContext, R.layout.avater_user_2, null);
                        mViewHolder.iv_avatar1 = (ImageView) avaterview.findViewById(R.id.iv_avatar1);
                        mViewHolder.iv_avatar2 = (ImageView) avaterview.findViewById(R.id.iv_avatar2);
                        Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar1);
                        Glide.with(mContext).load(avatar.get(1)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar2);
                        mViewHolder.avater_container.addView(avaterview);
                        break;
                    case 3:
                        avaterview = View.inflate(mContext, R.layout.avater_user_3, null);
                        mViewHolder.iv_avatar1 = (ImageView) avaterview.findViewById(R.id.iv_avatar1);
                        mViewHolder.iv_avatar2 = (ImageView) avaterview.findViewById(R.id.iv_avatar2);
                        mViewHolder.iv_avatar3 = (ImageView) avaterview.findViewById(R.id.iv_avatar3);
                        Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar1);
                        Glide.with(mContext).load(avatar.get(1)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar2);
                        Glide.with(mContext).load(avatar.get(2)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar3);
                        mViewHolder.avater_container.addView(avaterview);
                        break;
                    case 4:
                        avaterview = View.inflate(mContext, R.layout.avater_user_4, null);
                        mViewHolder.iv_avatar1 = (ImageView) avaterview.findViewById(R.id.iv_avatar1);
                        mViewHolder.iv_avatar2 = (ImageView) avaterview.findViewById(R.id.iv_avatar2);
                        mViewHolder.iv_avatar3 = (ImageView) avaterview.findViewById(R.id.iv_avatar3);
                        mViewHolder.iv_avatar4 = (ImageView) avaterview.findViewById(R.id.iv_avatar4);
                        Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar1);
                        Glide.with(mContext).load(avatar.get(1)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar2);
                        Glide.with(mContext).load(avatar.get(2)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar3);
                        Glide.with(mContext).load(avatar.get(3)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar4);
                        mViewHolder.avater_container.addView(avaterview);
                        break;
                    case 5:
                    default:
                        avaterview = View.inflate(mContext, R.layout.avater_user_5, null);
                        mViewHolder.iv_avatar1 = (ImageView) avaterview.findViewById(R.id.iv_avatar1);
                        mViewHolder.iv_avatar2 = (ImageView) avaterview.findViewById(R.id.iv_avatar2);
                        mViewHolder.iv_avatar3 = (ImageView) avaterview.findViewById(R.id.iv_avatar3);
                        mViewHolder.iv_avatar4 = (ImageView) avaterview.findViewById(R.id.iv_avatar4);
                        mViewHolder.iv_avatar5 = (ImageView) avaterview.findViewById(R.id.iv_avatar5);
                        Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar1);
                        Glide.with(mContext).load(avatar.get(1)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar2);
                        Glide.with(mContext).load(avatar.get(2)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar3);
                        Glide.with(mContext).load(avatar.get(3)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar4);
                        Glide.with(mContext).load(avatar.get(4)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(mViewHolder.iv_avatar5);
                        mViewHolder.avater_container.addView(avaterview);
                        break;

                    case 0:
                        break;
                }


            } else {
                mViewHolder.user_avatar.setBackgroundDrawable(null);
                if (conversation.getMsgType() == 1000) {
                    mViewHolder.user_avatar.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ECContacts contact = ContactSqlManager.getContact(conversation.getSessionId());
//                    mViewHolder.user_avatar.setImageBitmap(ContactLogic.getPhoto(contact.getRemark()));
                    Glide.with(context).load(avaterPath).error(R.mipmap.default_useravatar).into(mViewHolder.user_avatar);
                }
            }
            mViewHolder.image_mute.setVisibility(isNotice(conversation) ? View.GONE : View.VISIBLE);

            String contactId = conversation.getContactId();
            if (contactId.equals(SystemPush.SYS_ACCT_CHARGE)
                    || contactId.equals(SystemPush.SYS_ACCT_BONUS)
                    || contactId.equals(SystemPush.SYS_ACCT_WITHDRAWAL)
                    || contactId.equals(SystemPush.SYS_ACCT_REVIEW)
                    || contactId.equals(SystemPush.SYS_ACCT_ORDER)
                    || contactId.equals("18510829668")
                    || contactId.equals("18510829669")
                    || contactId.equals("182638268288")
                    || contactId.equals("182638268289")
                    ) {
                mViewHolder.tipcnt_tv.setVisibility(View.GONE);
                mViewHolder.prospect_iv.setVisibility(View.GONE);
                String lastmsg = getConversationSnippet(conversation).toString();
                Log.e("gy", "推送消息：" + lastmsg);
                try {
                    JSONObject sysmsgObj = (JSONObject) JSON.parse(lastmsg);
                    int type = sysmsgObj.getInteger("type");

                    switch (type) {
                        case SystemPush.TYPE_CHARGE:

                            if (sysmsgObj.toString().contains("提现")) {
                                mViewHolder.user_avatar.setImageResource(R.mipmap.sysmsg_1);
                                mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
                                mViewHolder.nickname_tv.setText("提现申请");
                            }
//                            else if (sysmsgObj.toString().contains("充值")) {
//                                mViewHolder.user_avatar.setImageResource(R.mipmap.sysmsg_0);
//                                mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
//                                mViewHolder.nickname_tv.setText("系统充值");
//                            } else if (sysmsgObj.toString().contains("邀请信息")) {
//                                mViewHolder.user_avatar.setImageResource(R.mipmap.yaoqing_chat);
//                                mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
//                                mViewHolder.nickname_tv.setText("好友邀请");
//                            } else if (sysmsgObj.toString().contains("广告")) {
//                                mViewHolder.user_avatar.setImageResource(R.mipmap.guanggao_chat);
//                                mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
//                                mViewHolder.nickname_tv.setText("广告通知");
//                            } else if (sysmsgObj.toString().contains("好友请求")) {
//                                mViewHolder.user_avatar.setImageResource(R.mipmap.add_friend2);
//                                mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
//                                mViewHolder.nickname_tv.setText("好友请求");
//                            } else if (sysmsgObj.toString().contains("退")) {
//                                mViewHolder.user_avatar.setImageResource(R.mipmap.exit_money);
//                                mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
//                                mViewHolder.nickname_tv.setText("退款通知");
//                            }
                            break;

                        case SystemPush.TYPE_BONUS:
                            mViewHolder.user_avatar.setImageResource(R.drawable.sysmsg_1);
                            mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
                            mViewHolder.nickname_tv.setText("系统分红");
                            break;

                        case SystemPush.TYPE_WITHDRAWAL:
                            mViewHolder.user_avatar.setImageResource(R.mipmap.sysmsg_0);
                            mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
                            mViewHolder.nickname_tv.setText("系统充值");
                            break;

                        case SystemPush.TYPE_REVIEW:
                            mViewHolder.user_avatar.setImageResource(R.drawable.sysmsg_3);
                            mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
                            mViewHolder.nickname_tv.setText("审核提醒");
                            break;

                        case SystemPush.TYPE_ORDER:
                            mViewHolder.user_avatar.setImageResource(R.drawable.sysmsg_4);
                            mViewHolder.last_msg_tv.setText(sysmsgObj.getString("message"));
                            mViewHolder.nickname_tv.setText("订单提醒");
                            break;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        boolean isTop = ConversationSqlManager.querySessionisTopBySessionId(conversation.getSessionId());
        if (isTop && !conversation.getSessionId().equals(GroupNoticeSqlManager.CONTACT_ID)) {
            view.setBackgroundColor(mContext.getResources().getColor(R.color.list_bg_gaoliang));
        } else {
            view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.list_item_selector));
        }

        return view;
    }

    private void handleDisplayNameTextColor(EmojiconTextView textView, String contactId) {
        if (ContactLogic.isCustomService(contactId)) {
            textView.setTextColor(colorStateLists[1]);
        } else {
            textView.setTextColor(colorStateLists[0]);
        }
    }

    /**
     * 设置未读图片显示规则
     *
     * @param mViewHolder
     * @param conversation
     */
    private void setConversationUnread(ViewHolder mViewHolder, Conversation conversation) {
        String msgCount = conversation.getUnreadCount() > 100 ? "..." : String.valueOf(conversation.getUnreadCount());
        mViewHolder.tipcnt_tv.setText(msgCount);
        if (conversation.getUnreadCount() == 0) {
            mViewHolder.tipcnt_tv.setVisibility(View.GONE);
            mViewHolder.prospect_iv.setVisibility(View.GONE);
        } else if (conversation.isNotice()) {
            mViewHolder.tipcnt_tv.setVisibility(View.VISIBLE);
            mViewHolder.prospect_iv.setVisibility(View.GONE);
        } else {
            mViewHolder.prospect_iv.setVisibility(View.VISIBLE);
            mViewHolder.tipcnt_tv.setVisibility(View.GONE);
        }
    }

    static class ViewHolder {
        ImageView user_avatar;
        TextView tipcnt_tv;
        ImageView prospect_iv;
        EmojiconTextView nickname_tv;
        TextView update_time_tv;
        EmojiconTextView last_msg_tv;
        ImageView image_input_text;
        ImageView image_mute;
        RelativeLayout avater_container;

        ImageView iv_avatar1;
        ImageView iv_avatar2;
        ImageView iv_avatar3;
        ImageView iv_avatar4;
        ImageView iv_avatar5;
    }

    @Override
    protected void initCursor() {
        notifyChange();
    }

    @Override
    public void notifyChange() {
        if (mCallBackListener != null) {
            mCallBackListener.OnListAdapterCallBack();
        }
        Cursor cursor = ConversationSqlManager.getConversationCursor();
        setCursor(cursor);
        isAtSession = ECPreferences.getSharedPreferences().getString(ECPreferenceSettings.SETTINGS_AT.getId(), "");
        super.notifyDataSetChanged();
    }

    private boolean isNotice(Conversation conversation) {
        if (conversation.getSessionId().toLowerCase().startsWith("g")) {
            return conversation.isNotice();
        }
        return true;
    }

}
