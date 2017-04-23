package com.kaichaohulian.baocms.ecdemo.storage;

/**
 * Created by Jorstin on 2015/7/15.
 */
public class MeetingSqlManager extends AbstractSQLManager {

    private static MeetingSqlManager sInstance = new MeetingSqlManager();

    private static MeetingSqlManager getInstance() {
        return sInstance;
    }


}
