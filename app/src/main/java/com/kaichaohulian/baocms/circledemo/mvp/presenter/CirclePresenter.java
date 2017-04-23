package com.kaichaohulian.baocms.circledemo.mvp.presenter;

import android.content.Context;
import android.view.View;

import com.kaichaohulian.baocms.circledemo.bean.CircleItem;
import com.kaichaohulian.baocms.circledemo.bean.CommentConfig;
import com.kaichaohulian.baocms.circledemo.bean.CommentItem;
import com.kaichaohulian.baocms.circledemo.bean.FavortItem;
import com.kaichaohulian.baocms.circledemo.mvp.modle.CircleModel;
import com.kaichaohulian.baocms.circledemo.mvp.modle.IDataRequestListener;
import com.kaichaohulian.baocms.circledemo.mvp.view.ICircleView;
import com.kaichaohulian.baocms.circledemo.utils.DatasUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * 
* @ClassName: CirclePresenter 
* @Description: 通知model请求服务器和通知view更新
* @author yiw
* @date 2015-12-28 下午4:06:03 
*
 */
public class CirclePresenter extends BasePresenter<ICircleView>{
	private CircleModel mCircleModel;
	private DatasUtil DatasUtil;

	public CirclePresenter(Context Context){
		mCircleModel = new CircleModel();
		DatasUtil = new DatasUtil(Context);
	}

	public void loadData(int loadType, JSONArray JSONArray) throws JSONException {
        List<CircleItem> datas = DatasUtil.createCircleDatas(JSONArray);
        getView().update2loadData(loadType, datas);
	}


	/**
	 * 
	* @Title: deleteCircle 
	* @Description: 删除动态 
	* @param  circleId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteCircle(final String circleId){
		mCircleModel.deleteCircle(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				getView().update2DeleteCircle(circleId);
			}
		});
	}
	/**
	 * 
	* @Title: addFavort 
	* @Description: 点赞
	* @param  circlePosition     
	* @return void    返回类型 
	* @throws
	 */
	public void addFavort(final int circlePosition){
		mCircleModel.addFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				FavortItem item = DatasUtil.createCurUserFavortItem();
				getView().update2AddFavorite(circlePosition, item);
			}
		});
	}
	/**
	 * 
	* @Title: deleteFavort 
	* @Description: 取消点赞 
	* @param @param circlePosition
	* @param @param favortId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteFavort(final int circlePosition, final String favortId){
		mCircleModel.deleteFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				getView().update2DeleteFavort(circlePosition, favortId);
			}
		});
	}
	
	/**
	 * 
	* @Title: addComment 
	* @Description: 增加评论
	* @param  content
	* @param  config  CommentConfig
	* @return void    返回类型 
	* @throws
	 */
	public void addComment(final String content, final CommentConfig config){
		if(config == null){
			return;
		}
		mCircleModel.addComment(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				CommentItem newItem = null;
				if (config.commentType == CommentConfig.Type.PUBLIC) {
					newItem = DatasUtil.createPublicComment(content);
				} else if (config.commentType == CommentConfig.Type.REPLY) {
					newItem = DatasUtil.createReplyComment(config.replyUser, content);
				}

				getView().update2AddComment(config.circlePosition, newItem);
			}

		});
	}
	
	/**
	 * 
	* @Title: deleteComment 
	* @Description: 删除评论 
	* @param @param circlePosition
	* @param @param commentId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteComment(final int circlePosition, final String commentId){
		mCircleModel.deleteComment(new IDataRequestListener(){

			@Override
			public void loadSuccess(Object object) {
				getView().update2DeleteComment(circlePosition, commentId);
			}
			
		});
	}

	/**
	 *
	 * @param commentConfig
	 */
	public void showEditTextBody(CommentConfig commentConfig){
		getView().updateEditTextBodyVisible(View.VISIBLE, commentConfig);
	}

}
