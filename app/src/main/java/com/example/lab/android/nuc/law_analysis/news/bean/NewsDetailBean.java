package com.example.lab.android.nuc.law_analysis.news.bean;

import java.util.List;

public class NewsDetailBean {

    private String template;
    private String body;
    private int replyCount;
    private int threadVote;
    private String shareLink;
    private String digest;
    private String dkeys;
    private int threadAgainst;
    private String ec;
    private String docid;
    private String title;
    private boolean picnews;
    private String replyBoard;
    private String source;
    private boolean hasNext;
    private String voicecomment;
    private String tid;
    private String ptime;
    private List<?> ydbaike;
    private List<?> link;
    private List<?> votes;
    private List<ImgBean> img;
    private List<TopiclistNewsBean> topiclist_news;
    private List<?> boboList;
    private List<?> topiclist;
    private List<?> book;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getThreadVote() {
        return threadVote;
    }

    public void setThreadVote(int threadVote) {
        this.threadVote = threadVote;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDkeys() {
        return dkeys;
    }

    public void setDkeys(String dkeys) {
        this.dkeys = dkeys;
    }

    public int getThreadAgainst() {
        return threadAgainst;
    }

    public void setThreadAgainst(int threadAgainst) {
        this.threadAgainst = threadAgainst;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPicnews() {
        return picnews;
    }

    public void setPicnews(boolean picnews) {
        this.picnews = picnews;
    }

    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public String getVoicecomment() {
        return voicecomment;
    }

    public void setVoicecomment(String voicecomment) {
        this.voicecomment = voicecomment;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public List<?> getYdbaike() {
        return ydbaike;
    }

    public void setYdbaike(List<?> ydbaike) {
        this.ydbaike = ydbaike;
    }

    public List<?> getLink() {
        return link;
    }

    public void setLink(List<?> link) {
        this.link = link;
    }

    public List<?> getVotes() {
        return votes;
    }

    public void setVotes(List<?> votes) {
        this.votes = votes;
    }

    public List<ImgBean> getImg() {
        return img;
    }

    public void setImg(List<ImgBean> img) {
        this.img = img;
    }

    public List<TopiclistNewsBean> getTopiclist_news() {
        return topiclist_news;
    }

    public void setTopiclist_news(List<TopiclistNewsBean> topiclist_news) {
        this.topiclist_news = topiclist_news;
    }

    public List<?> getBoboList() {
        return boboList;
    }

    public void setBoboList(List<?> boboList) {
        this.boboList = boboList;
    }

    public List<?> getTopiclist() {
        return topiclist;
    }

    public void setTopiclist(List<?> topiclist) {
        this.topiclist = topiclist;
    }

    public List<?> getBook() {
        return book;
    }

    public void setBook(List<?> book) {
        this.book = book;
    }

    public static class ImgBean {
        /**
         * ref : <!--IMG#0-->
         * pixel : 900*706
         * alt :
         * src : http://cms-bucket.nosdn.127.net/catchpic/6/60/600730dc7d5e3664f92c3810eabcc46a.jpg
         */

        private String ref;
        private String pixel;
        private String alt;
        private String src;

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getPixel() {
            return pixel;
        }

        public void setPixel(String pixel) {
            this.pixel = pixel;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }

    public static class TopiclistNewsBean {
        /**
         * hasCover : false
         * subnum : 3.2万
         * alias : Top News
         * tname : 头条
         * ename : androidnews
         * tid : T1348647909107
         * cid : C1348646712614
         */

        private boolean hasCover;
        private String subnum;
        private String alias;
        private String tname;
        private String ename;
        private String tid;
        private String cid;

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public String getSubnum() {
            return subnum;
        }

        public void setSubnum(String subnum) {
            this.subnum = subnum;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }
}
