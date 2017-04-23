package com.kaichaohulian.baocms.circledemo.mvp.view;

import com.kaichaohulian.baocms.circledemo.bean.CircleItem;
import com.kaichaohulian.baocms.circledemo.bean.CommentConfig;
import com.kaichaohulian.baocms.circledemo.bean.CommentItem;
import com.kaichaohulian.baocms.circledemo.bean.FavortItem;

import java.util.List;

/**
 * 
* @ClassName: ICircleViewUpdateListener 
* @Description: view,服务器响应后更新界面 
* @author yiw
* @date 2015-12-28 下午4:13:04 
*
 */
public interface ICircleView extends BaseView{

	void update2DeleteCircle(String circleId);
	void update2AddFavorite(int circlePosition, FavortItem addItem);
	void update2DeleteFavort(int circlePosition, String favortId);
	void update2AddComment(int circlePosition, CommentItem addItem);
	void update2DeleteComment(int circlePosition, String commentId);

	void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);

	void update2loadData(int loadType, List<CircleItem> datas);
}


