package com.xiaoqianchang.gankreader.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * $desc$
 * <p>
 * Created by Chang.Xiao on 2017/2/8.
 *
 * @version 1.0
 */

public class RspGank implements Serializable {

    private boolean error;

    private List<Gank> results;

    public static class Gank {

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public String getSource() {
            return source;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public boolean isUsed() {
            return used;
        }

        public String getWho() {
            return who;
        }

        public List<String> getImages() {
            return images;
        }
    }
}
