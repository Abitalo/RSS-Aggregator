package com.abitalo.www.rss_aggregator.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.abitalo.www.rss_aggregator.WebActivity;
import com.abitalo.www.rss_aggregator.constants.Conf;
import com.abitalo.www.rss_aggregator.entity.Article;
import com.abitalo.www.rss_aggregator.entity.MeetUser;
import com.abitalo.www.rss_aggregator.entity.UserData;
import com.abitalo.www.rss_aggregator.entity.UserDataToArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Lancelot on 2016/6/22.
 */
public class FavoriteHelper{
    private Context context;
        private String userName;
        private String title;
        private String author;
        private String content;
        private String userId;
        private String userDataId;
        private String articleId;
        private Integer facetId;
        public static final int ADD = 0;
        public static final int DELETE = 1;
        public static final int CHECK=2;

    public FavoriteHelper(){

    }

    public void init(Context context,String title,String author,String content,Integer facetId){
        this.context = context;
        this.title = title;
        this.author = author;
        this.content = content;
        this.facetId=facetId;

        SharedPreferences sharedPreferences= context.getSharedPreferences("userAuthentication", Activity.MODE_PRIVATE);
        userName =sharedPreferences.getString("name", "");
    }

    public void query(final int option){
        BmobQuery<MeetUser> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username", userName);
        bmobQuery.addQueryKeys("objectId");

        bmobQuery.findObjects(context, new FindListener<MeetUser>(){

            @Override
            public void onSuccess(List<MeetUser> list) {
                if (list.size() > 0){
                    userId = list.get(0).getObjectId();
                    getUserDataId(option);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper", "code:::"+i);
                Log.i("RegisterHelper", "Message:::"+s);
            }
        });
    }

    private void getUserDataId(final int option){
        BmobQuery<UserData> bmobQuery = new BmobQuery("UserData");
        bmobQuery.addWhereEqualTo("userId", userId);
        bmobQuery.findObjects(context, new FindListener<UserData>() {
            @Override
            public void onSuccess(List<UserData> list) {
                userDataId=list.get(0).getObjectId();
                accumulateTendency();
                UserData userData=list.get(0);
                if(option==ADD){
                    addFavorite();
                    userData.getTendencies().set(facetId,userData.getTendencies().get(facetId)+ Conf.FAVORITE_ARTICLE);
                    userData.update(context);
                }else if(option==DELETE){
                    removeFavorite();
                    userData.getTendencies().set(facetId,userData.getTendencies().get(facetId)- Conf.FAVORITE_ARTICLE);
                    userData.update(context);
                }else if(option==CHECK){
                    queryLiked();
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i("RegisterHelper","findUserData error!!!");
            }
        });
    }

    private void accumulateTendency(){
        BmobQuery<UserData> query=new BmobQuery<>();
        query.getObject(context, userDataId, new GetListener<UserData>() {
            @Override
            public void onSuccess(UserData userData) {
                userData.getTendencies().set(facetId,userData.getTendencies().get(facetId)+ Conf.GLANCE_ARTICLE);
                userData.update(context);
                Log.i("abitalo!!","shitshitshit");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("abitalo!!",s);
            }
        });
    }
    private void queryLiked(){
        BmobQuery<Article> bmobQuery=new BmobQuery<>("article");
        bmobQuery.addWhereEqualTo("title",title);
        bmobQuery.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if(null == jsonArray || jsonArray.length()==0){
                    //该title未被任何用户收藏过，更不可能被该用户收藏
                    ((WebActivity)context).onQueryFinished(false);
                }else{
                    try{
                        String objectId=jsonArray.getJSONObject(0).get("objectId").toString();
                        BmobQuery<UserDataToArticle> query=new BmobQuery<>("userdata_to_article");
                        List<BmobQuery<UserDataToArticle>> conditions=new ArrayList<>();
                        conditions.add(new BmobQuery<UserDataToArticle>().addWhereEqualTo("userDataId",userDataId));
                        conditions.add(new BmobQuery<UserDataToArticle>().addWhereEqualTo("articleId",objectId));
                        query.and(conditions);
                        query.findObjects(context, new FindCallback() {
                            @Override
                            public void onSuccess(JSONArray jsonArray) {
                                if(null == jsonArray || jsonArray.length()==0)
                                    //中间表找不到记录，该文章未被该用户收藏
                                    ((WebActivity)context).onQueryFinished(false);
                                else{
                                    //中间表找到记录，该文章被该用户收藏了
                                    ((WebActivity)context).onQueryFinished(true);
                                }
                            }
                            @Override
                            public void onFailure(int i, String s) {
                            }
                        });
                    }catch (JSONException e){
                        Log.i("abitalo",e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    private void removeFavorite() {
        deleteByMidId();
    }


    private void addFavorite() {
        addByArticleId();
    }

    //有就拿了更新，没有就建立了再拿
    private void addByArticleId(){
        BmobQuery<Article> articleBmobQuery=new BmobQuery<>("article");
        articleBmobQuery.addWhereEqualTo("title",title);
        articleBmobQuery.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if(null == jsonArray || jsonArray.length()==0){
                    final Article newArticle=new Article();
                    newArticle.setTitle(title);
                    newArticle.setWriter(author);
                    newArticle.setContent(content);
                    newArticle.setFavoritedTimes(1);
                    newArticle.setFacetIdNumber(facetId);
                    newArticle.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            addUserDataToArticle(newArticle.getObjectId());
                            ((WebActivity)context).onQueryFinished(true);
                            Log.i("RegisterHelper","新建articleId"+newArticle.getObjectId());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.i("RegisterHelper","新建失败"+newArticle.getObjectId());
                        }
                    });
                }else{
                    try{
                        final Article newArticle=new Article();
                        JSONObject obj=jsonArray.getJSONObject(0);
                        newArticle.setFavoritedTimes(((Integer)obj.get("favoritedTimes"))+1);
                        newArticle.update(context, obj.get("objectId").toString(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                ((WebActivity)context).onQueryFinished(true);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Log.i("Abitalo",s);
                            }
                        });
                        String objectId= obj.get("objectId").toString();
                        addUserDataToArticle(objectId);
                    }catch (JSONException e){
                        Log.i("Abitalo",e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }

    //先用title找到articleid，然后复合条件找到中间表id，然后删除
    private void deleteByMidId(){
        BmobQuery<Article> bmobQuery=new BmobQuery<>("article");
        bmobQuery.addWhereEqualTo("title",title);
        bmobQuery.findObjects(context, new FindCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if(null == jsonArray || jsonArray.length()==0){
                    ((WebActivity)context).onQueryFinished(false);
                }else{
                    try{
                        //收藏数-1
                        String objectId=jsonArray.getJSONObject(0).get("objectId").toString();
                        Integer favoritedTimes=(Integer)jsonArray.getJSONObject(0).get("favoritedTimes");
                        Article article=new Article();
                        article.setFavoritedTimes(favoritedTimes-1);
                        article.update(context, objectId, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Log.i("Abitalo",s);
                            }
                        });

                        BmobQuery<UserDataToArticle> query=new BmobQuery<>("userdata_to_article");
                        final List<BmobQuery<UserDataToArticle>> conditions=new ArrayList<>();
                        conditions.add(new BmobQuery<UserDataToArticle>().addWhereEqualTo("userDataId",userDataId));
                        conditions.add(new BmobQuery<UserDataToArticle>().addWhereEqualTo("articleId",objectId));
                        query.and(conditions);
                        query.findObjects(context, new FindCallback() {
                            @Override
                            public void onSuccess(JSONArray jsonArray)  {
                                try {
                                    String objectId=jsonArray.getJSONObject(0).get("objectId").toString();
                                    UserDataToArticle u_to_a=new UserDataToArticle();
                                    u_to_a.setObjectId(objectId);
                                    u_to_a.delete(context);
                                    ((WebActivity)context).onQueryFinished(false);
                                }catch (JSONException e){
                                    Log.i("abitalo",e.getMessage());
                                }
                            }
                            @Override
                            public void onFailure(int i, String s) {
                            }
                        });
                    }catch (JSONException e){
                        Log.i("abitalo",e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("abitalo",s);
            }
        });
    }

    private void addUserDataToArticle(String articleId){
        UserDataToArticle u_to_a=new UserDataToArticle();
        u_to_a.setUserDataId(userDataId);
        u_to_a.setArticleId(articleId);
        u_to_a.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.i("RegisterHelper","save ok");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i("RegisterHelper","save error");
            }
        });
    }

}